package com.creator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.COSConstants;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;
import com.creator.domain.vo.UserInfoVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.COSOperate;
import com.creator.utils.PathUtils;
import com.creator.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

