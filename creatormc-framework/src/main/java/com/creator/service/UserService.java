package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddUserDto;
import com.creator.domain.dto.GetPageUserListDto;
import com.creator.domain.dto.UpdateUserDto;
import com.creator.domain.dto.UpdateUserPasswordDto;
import com.creator.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * @param dto
     * @return
     */
    ResponseResult getPageUserList(Integer pageNum, Integer pageSize, GetPageUserListDto dto);

    /**
     * 添加用户
     * @param addUserDto
     * @return
     */
    ResponseResult addUser(AddUserDto addUserDto);

    /**
     * 删除用户
     * @param id 用户 id
     * @return
     */
    ResponseResult deleteUser(List<Long> id);

    /**
     * 查询单个用户
     * @param id 用户 id
     * @return
     */
    ResponseResult getUser(Long id);

    /**
     * 更新用户
     * @param updateUserDto
     * @return
     */
    ResponseResult updateUser(UpdateUserDto updateUserDto);

    /**
     * 更新用户状态
     * @param user
     * @return
     */
    ResponseResult changeUserStatus(User user);

    /**
     * 获取指定id的用户信息
     * @param id 用户id
     * @return
     */
    ResponseResult getUserInfo(Long id);

    /**
     * 更新用户密码
     * @param updateUserPasswordDto
     * @return
     */
    ResponseResult updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto);
}

