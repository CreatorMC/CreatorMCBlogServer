package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-27 22:00:54
 */
public interface UserService extends IService<User> {

    /**
     * 获取当前用户信息
     * @return
     */
    ResponseResult userInfo();
}

