package com.creator.handler.security;

import com.alibaba.fastjson.JSON;
import com.creator.domain.ResponseResult;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //处理认证异常
        //BadCredentialsException 登录时的用户名或密码错误异常
        //InternalAuthenticationServiceException 用户不存在的异常（数据库内查询不到用户）
        //InsufficientAuthenticationException 访问需要登录的接口，但还没有登录（没有token）时发生的异常
        authException.printStackTrace();

        ResponseResult result;
        if(authException instanceof BadCredentialsException || authException instanceof InternalAuthenticationServiceException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), authException.getMessage());
        } else if (authException instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证失败");
        }


        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
