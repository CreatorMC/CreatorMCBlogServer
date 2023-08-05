package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.RegisterDto;
import com.creator.domain.dto.UpdateUserInfoDto;
import com.creator.domain.entity.User;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/user")
@Api("用户")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取当前用户信息")
    @SystemLog(businessName = "获取当前用户信息")
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @ApiOperation("更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "头像文件", paramType = "form"),
            @ApiImplicitParam(name = "email", value = "邮箱", defaultValue = "test@qq.com", paramType = "form"),
            @ApiImplicitParam(name = "nickName", value = "昵称", defaultValue = "测试昵称", paramType = "form"),
            @ApiImplicitParam(name = "sex", value = "性别（0男，1女，2未知）", defaultValue = "1", paramType = "form")
    })
    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(MultipartFile file, UpdateUserInfoDto updateUserInfoDto) {
        return userService.updateUserInfo(file, BeanCopyUtils.copyBean(updateUserInfoDto, User.class));
    }

    @ApiOperation("用户注册")
    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDto registerDto) {
        return userService.register(BeanCopyUtils.copyBean(registerDto, User.class));
    }
}
