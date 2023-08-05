package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.RegisterDto;
import com.creator.domain.dto.UpdateUserInfoDto;
import com.creator.domain.entity.User;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @SystemLog(businessName = "获取当前用户信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(MultipartFile file, UpdateUserInfoDto updateUserInfoDto) {
        return userService.updateUserInfo(file, BeanCopyUtils.copyBean(updateUserInfoDto, User.class));
    }

    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody RegisterDto registerDto) {
        return userService.register(BeanCopyUtils.copyBean(registerDto, User.class));
    }
}
