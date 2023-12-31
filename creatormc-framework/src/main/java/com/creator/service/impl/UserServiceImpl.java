package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.COSConstants;
import com.creator.constants.SystemConstants;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddUserDto;
import com.creator.domain.dto.GetPageUserListDto;
import com.creator.domain.dto.UpdateUserDto;
import com.creator.domain.dto.UpdateUserPasswordDto;
import com.creator.domain.entity.Menu;
import com.creator.domain.entity.Role;
import com.creator.domain.entity.User;
import com.creator.domain.entity.UserRole;
import com.creator.domain.vo.*;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.MenuService;
import com.creator.service.RoleService;
import com.creator.service.UserRoleService;
import com.creator.service.UserService;
import com.creator.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private RedisCache redisCache;

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
    public ResponseResult getUserInfo(Long id) {
        //根据用户id查询用户信息
        User user = getById(id);
        //封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {
        //检查验证码是否与 Redis 中的验证码一致
        String vCode = redisCache.getCacheObject(updateUserPasswordDto.getUuid());
        if(!StringUtils.hasText(vCode) || !vCode.equals(updateUserPasswordDto.getVCode())) {
            //验证码不一致
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_CODE_NOT_EQUALS);
        }
        //验证码一致，更新用户密码
        //加密密码
        User user = new User();
        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
        boolean isSuccess = update(user, new LambdaQueryWrapper<User>()
                .eq(User::getEmail, updateUserPasswordDto.getEmail())
        );
        if(!isSuccess) {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_PASSWORD_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateUserInfo(MultipartFile file, User user) {
        //从token中获取用户id
        Long userId = SecurityUtils.getUserId();

        //为null的字段不会被更新，所以没必要检查
        //检查是否有其他用户使用了此昵称
        if(!Objects.isNull(user.getNickName()) && userDataExist(User::getNickName, user.getNickName(), userId)) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //检查邮箱是否已被其他用户使用
        if(!Objects.isNull(user.getEmail()) && userDataExist(User::getEmail, user.getEmail(), userId)) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //判断非空才删除并上传头像，当用户没有修改头像时，file为null
        if(!Objects.isNull(file)) {
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
        }
        user.setId(userId);
        //更新数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //新注册用户默认性别
        user.setSex(SystemConstants.USER_SEX_UNKNOWN);
        saveUser(user);
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
        return ResponseResult.okResult(new UserAdminInfoVo(perms, roles, userInfoVo));
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

    @Override
    public ResponseResult getPageUserList(Integer pageNum, Integer pageSize, GetPageUserListDto dto) {
        //分页查询
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<User>()
                //根据用户名模糊搜索
                .like(StringUtils.hasText(dto.getUserName()), User::getUserName, dto.getUserName())
                //根据手机号模糊搜索
                .like(StringUtils.hasText(dto.getPhonenumber()), User::getPhonenumber, dto.getPhonenumber())
                //根据状态进行查询
                .eq(StringUtils.hasText(dto.getStatus()), User::getStatus, dto.getStatus())
        );
        List<UserAdminListVo> userAdminListVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserAdminListVo.class);
        return ResponseResult.okResult(new PageVo(userAdminListVos, page.getTotal()));
    }

    @Override
    @Transactional  //开启事务
    public ResponseResult addUser(AddUserDto addUserDto) {
        //保存用户
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        saveUser(user);
        //保存用户与角色的关联信息
        List<UserRole> userRoles = addUserDto.getRoleIds().stream().map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(List<Long> id) {
        removeByIds(id);
        return ResponseResult.okResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseResult getUser(Long id) {
        //查询用户所关联的角色id列表
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, id)
        );
        //得到角色id列表
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        //查询所有正常状态的角色列表
        List<Role> roles = (List<Role>) roleService.getRoleList().getData();
        //查询用户信息
        UserAdminGetVo user = BeanCopyUtils.copyBean(getById(id), UserAdminGetVo.class);
        //封装
        return ResponseResult.okResult(new UserAdminVo(roleIds, roles, user));
    }

    @Override
    @Transactional  //开启事务
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        //为null的字段不会被更新，所以也没必要检查
        //检查是否有其他用户使用了此用户名
        if(!Objects.isNull(updateUserDto.getUserName()) && userDataExist(User::getUserName , updateUserDto.getUserName(), updateUserDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //检查是否有其他用户使用了此昵称
        if(!Objects.isNull(updateUserDto.getNickName()) && userDataExist(User::getNickName, updateUserDto.getNickName(), updateUserDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //检查邮箱是否已被其他用户使用
        if(!Objects.isNull(updateUserDto.getEmail()) && userDataExist(User::getEmail, updateUserDto.getEmail(), updateUserDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //检查手机号是否已被其他用户使用
        if(!Objects.isNull(updateUserDto.getPhonenumber()) && userDataExist(User::getPhonenumber, updateUserDto.getPhonenumber(), updateUserDto.getId())) {
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }

        //更新用户
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        //采用自己的方法，当封禁时间为 null 时也更新。
        getBaseMapper().updateByIdAndBanEndTime(user);
        //updateById(user);
        //删除原来用户与角色的关联
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, user.getId())
        );
        //插入现在用户与角色的关联
        List<UserRole> userRoles = updateUserDto.getRoleIds().stream().map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeUserStatus(User user) {
        updateById(user);
        return ResponseResult.okResult();
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
     * 将用户保存到数据库并判断是否符合要求<br>
     * 加锁保证唯一性
     * @param user
     */
    private synchronized void saveUser(User user) {
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
        if(userDataExist(User::getUserName , user.getUserName())) {
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
     * 除特定用户外，判断是否存在该字符串数据
     * @param column 列
     * @param data 数据
     * @param userId 排除的用户 id
     * @return
     */
    private boolean userDataExist(SFunction<User, ?> column, String data, Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(column, data);
        queryWrapper.ne(User::getId, userId);
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

