package com.creator.service;

import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;

@SuppressWarnings("rawtypes")
public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}

