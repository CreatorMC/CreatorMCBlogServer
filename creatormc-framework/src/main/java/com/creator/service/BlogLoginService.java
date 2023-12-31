package com.creator.service;

import com.creator.domain.ResponseResult;
import com.creator.domain.entity.User;

@SuppressWarnings("rawtypes")
public interface BlogLoginService {
    /**
     * 用户名登录
     * @param user
     * {
     *  userName: "xxx",
     *  password: "xxx"
     * }
     * @return
     * {
     * 	"code": 200,
     * 	"data": {
     * 		"token": "xxx",
     * 		"userInfo": {
     * 			"avatar": "xxx",
     * 			"email": "xxx",
     * 			"id": 1,
     * 			"nickName": "xxx",
     * 			"sex": "1"
     *      }
     *  },
     * 	"msg": "操作成功"
     * }
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     * @return
     */
    ResponseResult logout();
}
