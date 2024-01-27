package com.creator.controller;

import com.creator.funnellog.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.LoginDto;
import com.creator.domain.entity.User;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.LoginService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/user")
@Api("登录退出")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("用户名登录")
    @SystemLog(businessName = "后台用户名登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        if(!StringUtils.hasText(loginDto.getUserName())) {
            //没传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(BeanCopyUtils.copyBean(loginDto, User.class));
    }

    @ApiOperation("退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "后台退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }

    @ApiOperation("获取随机图片")
    @SystemLog(businessName = "获取随机图片")
    @GetMapping("/randomImg")
    public ResponseResult getRandomImg(@RequestHeader("User-Agent") String userAgent) {
        return loginService.getRandomImg(userAgent);
    }
}
