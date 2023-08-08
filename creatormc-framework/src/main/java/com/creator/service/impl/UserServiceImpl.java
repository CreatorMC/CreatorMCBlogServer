package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.COSConstants;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Menu;
import com.creator.domain.entity.User;
import com.creator.domain.vo.AdminUserInfoVo;
import com.creator.domain.vo.MenuArrayVo;
import com.creator.domain.vo.MenuVo;
import com.creator.domain.vo.UserInfoVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.MenuService;
import com.creator.service.RoleService;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.COSOperate;
import com.creator.utils.PathUtils;
import com.creator.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-27 22:00:54
 */
@SuppressWarnings("rawtypes")
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private COSOperate cosOperate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(MultipartFile file, User user) {
        //TODO 修改个人信息时也需判断昵称等是否已存在
        //从token中获取用户id
        Long userId = SecurityUtils.getUserId();
        //获取旧头像路径
        String oldAvatar = getById(userId).getAvatar();
        if(StringUtils.hasText(oldAvatar)) {
            //如果存在旧头像，拼接得到旧头像的key
            String key = COSConstants.COS_HEAD_DIR + oldAvatar.substring(oldAvatar.lastIndexOf('/') + 1);
            //删除旧头像
            cosOperate.deleteFile(key);
        }
        //上传头像到COS，并获取链接
        String url = uploadImg(file);
        user.setAvatar(url);
        user.setId(userId);
        //更新数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //TODO 多线程下有可能同时都判断为不冲突，但结果两个人的昵称都相同。 可以在数据库中设置唯一性约束解决此问题吗？
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userDataExist(User::getUserName ,user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(userDataExist(User::getNickName, user.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(userDataExist(User::getEmail, user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getAdminUserInfo() {
        //从上下文中得到用户id
        Long userId = SecurityUtils.getUserId();
        //通过用户id查询用户信息
        User user = getById(userId);
        //得到用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //根据用户id查询用户拥有的角色的role_key
        List<String> roles = roleService.selectRoleKeysByUserId(userId);
        //获取权限数组
        List<String> perms = menuService.selectMenuPermsByUserId(userId);
        return ResponseResult.okResult(new AdminUserInfoVo(perms, roles, userInfoVo));
    }

    @Override
    public ResponseResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询该用户可用的菜单
        List<Menu> menus = menuService.selectRouters(userId);
        //转换为MenuVo
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        //存放根菜单的列表
        List<MenuVo> menuVoList = new ArrayList<>();
        //查找根菜单的子菜单
        for (int i = 0; i < menuVos.size(); i++) {
            if(menuVos.get(i).getParentId().equals(0L)) {
                //根菜单，找它下面还有哪些子菜单
                findSubMenu(menuVos.get(i), menuVos);
                menuVoList.add(menuVos.get(i));
            }
        }
        return ResponseResult.okResult(new MenuArrayVo(menuVoList));
    }

    /**
     * 查找根菜单下有哪些子菜单
     * @param menuVo 父菜单
     * @param menuVos 菜单列表
     * @return
     */
    private void findSubMenu(MenuVo menuVo, List<MenuVo> menuVos) {
        for (MenuVo m: menuVos) {
            if(menuVo.getId().equals(m.getParentId())) {
                //如果父菜单的id等于子菜单的父id，那么当前这个菜单是父菜单的子菜单
                menuVo.getChildren().add(m);
                findSubMenu(m, menuVos);
            }
        }
    }

    /**
     * 判断是否存在该字符串数据
     * @param column 列
     * @param data 数据
     * @return
     */
    private boolean userDataExist(SFunction<User, ?> column, String data) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, data);
        return count(queryWrapper) > 0;
    }

    /**
     * 上传用户头像
     * @param img
     * @return url
     */
    private String uploadImg(MultipartFile img) {
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对文件类型进行判断
        //noinspection ConstantConditions
        if(!(originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //判断文件大小
        if(img.getSize() >= COSConstants.HEAD_SIZE_LIMIT) {
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        String key = PathUtils.generateFilePath(COSConstants.COS_HEAD_DIR, originalFilename);
        //如果判断通过上传文件到COS
        return cosOperate.uploadFile(key, img);
    }
}

