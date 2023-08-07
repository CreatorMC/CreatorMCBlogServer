package com.creator.controller;

import com.creator.domain.ResponseResult;
import com.creator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getInfo")
    public ResponseResult getAdminUserInfo() {
        return userService.getAdminUserInfo();
    }
}
