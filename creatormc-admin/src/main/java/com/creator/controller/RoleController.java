package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddRoleDto;
import com.creator.domain.dto.RoleListDto;
import com.creator.domain.dto.RoleStatusDto;
import com.creator.domain.entity.Role;
import com.creator.service.RoleService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/system/role")
@Api("角色")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("分页查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "分页查询角色列表")
    @GetMapping("/list")
    public ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {
        return roleService.getPageRoleList(pageNum, pageSize, roleListDto);
    }

    @ApiOperation("更新角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新角色状态")
    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody RoleStatusDto roleStatusDto) {
        return roleService.changeRoleStatus(BeanCopyUtils.copyBean(roleStatusDto, Role.class));
    }

    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加角色")
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }
}
