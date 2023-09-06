package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddUserDto;
import com.creator.domain.dto.ChangeUserStatusDto;
import com.creator.domain.dto.GetPageUserListDto;
import com.creator.domain.dto.UpdateUserDto;
import com.creator.domain.entity.User;
import com.creator.service.UserService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

    @ApiOperation("分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "query", required = true)
    })
    @SystemLog(businessName = "分页查询用户列表")
    @PreAuthorize("@ps.hasPermission('system:user:query')") //用户查询
    @GetMapping("/system/user/list")
    public ResponseResult getPageUserList(Integer pageNum, Integer pageSize, GetPageUserListDto dto) {
        return userService.getPageUserList(pageNum, pageSize, dto);
    }

    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加用户")
    @PreAuthorize("@ps.hasPermission('system:user:add')")   //用户新增
    @PostMapping("/system/user")
    public ResponseResult addUser(@RequestBody AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "用户id（可以多个）", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "删除用户")
    @PreAuthorize("@ps.hasPermission('system:user:remove')")    //用户删除
    @DeleteMapping("/system/user/{id}")
    public ResponseResult deleteUser(@PathVariable Long ...id) {
        return userService.deleteUser(Arrays.asList(id));
    }

    @ApiOperation("查询单个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "查询单个用户")
    @GetMapping("/system/user/{id}")
    public ResponseResult getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @ApiOperation("更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新用户")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")  //用户修改
    @PutMapping("/system/user")
    public ResponseResult updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @ApiOperation("更新用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新用户状态")
    @PreAuthorize("@ps.hasPermission('system:user:edit')")  //用户修改
    @PutMapping("/system/user/changeStatus")
    public ResponseResult changeUserStatus(@RequestBody ChangeUserStatusDto dto) {
        return userService.changeUserStatus(BeanCopyUtils.copyBean(dto, User.class));
    }
}
