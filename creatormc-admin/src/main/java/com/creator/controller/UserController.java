package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@Api("用户")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "后台获取当前用户信息")
    @GetMapping("/getInfo")
    public ResponseResult getAdminUserInfo() {
        return userService.getAdminUserInfo();
    }

    @ApiOperation("获取用户所能访问的菜单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "后台获取用户所能访问的菜单数据")
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        return userService.getRouters();
    }
}
