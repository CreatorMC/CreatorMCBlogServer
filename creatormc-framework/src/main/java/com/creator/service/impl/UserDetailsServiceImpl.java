package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.creator.constants.SystemConstants;
import com.creator.dao.UserDao;
import com.creator.domain.entity.LoginUser;
import com.creator.domain.entity.User;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        User user = userDao.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, username));
        //判断是否查到用户  如果没查到抛出异常
        if(Objects.isNull(user)) {
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        //查询权限信息，并进行封装
        if(SystemConstants.USER_TYPE_ADMIN.equals(user.getType())) {
            //判断是后台用户
            List<String> perms = menuService.selectMenuPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }
        //查到用户  返回用户信息
        return new LoginUser(user, null);
    }
}
