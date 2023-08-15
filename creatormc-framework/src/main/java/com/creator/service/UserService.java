package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.UserListDto;
import com.creator.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-07-27 22:00:54
 */
@SuppressWarnings("rawtypes")
public interface UserService extends IService<User> {

    /**
     * 前台获取当前用户信息
     * @return
     */
    ResponseResult userInfo();

    /**
     * 更新用户信息
     * @param file
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(MultipartFile file, User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseResult register(User user);

    /**
     * 后台获取当前用户信息
     * @return
     */
    ResponseResult getAdminUserInfo();

    /**
     * 后台获取用户所能访问的菜单数据
     * @return
     */
    ResponseResult getRouters();

    /**
     * 分页查询用户列表
     * @param pageNum 第几页
     * @param pageSize 一页几条记录
     * @param userListDto
     * @return
     */
    ResponseResult getPageUserList(Integer pageNum, Integer pageSize, UserListDto userListDto);
}

