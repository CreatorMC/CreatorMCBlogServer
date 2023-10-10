package com.creator.service;

import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;

@SuppressWarnings("rawtypes")
public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();

    /**
     * 随机选择一张图片返回
     * @param userAgent UA字符串
     * @return
     */
    ResponseResult getRandomImg(String userAgent);

    /**
     * 发送邮箱验证码
     * @param email 电子邮箱
     * @return
     */
    ResponseResult sendEmailVCode(String email);
}

