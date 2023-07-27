package com.creator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.UserDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;
import com.creator.domain.vo.UserInfoVo;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-27 22:00:54
 */
@SuppressWarnings("rawtypes")
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

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
}

