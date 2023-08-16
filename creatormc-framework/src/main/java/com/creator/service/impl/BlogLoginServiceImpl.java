package com.creator.service.impl;

import com.creator.constants.SystemConstants;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.LoginUser;
import com.creator.domain.entity.User;
import com.creator.domain.vo.UserLoginVo;
import com.creator.domain.vo.UserInfoVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.service.BlogLoginService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.JwtUtil;
import com.creator.utils.RedisCache;
import com.creator.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@SuppressWarnings({"rawtypes", "DuplicatedCode"})
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        //获取userid，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_BLOG_KEY + userId, loginUser);
        //把token和userinfo封装返回
        UserLoginVo vo = new UserLoginVo(jwt, BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        redisCache.deleteObject(SystemConstants.LOGIN_BLOG_KEY + SecurityUtils.getUserId());
        return ResponseResult.okResult();
    }
}
