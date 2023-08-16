package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddRoleDto;
import com.creator.domain.dto.ChangeRoleStatusDto;
import com.creator.domain.dto.GetPageRoleListDto;
import com.creator.domain.dto.UpdateRoleDto;
import com.creator.domain.entity.Role;
import com.creator.service.RoleService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@ps.hasPermission('system:role:query')") //角色查询
    @GetMapping("/list")
    public ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, GetPageRoleListDto dto) {
        return roleService.getPageRoleList(pageNum, pageSize, dto);
    }

    @ApiOperation("更新角色状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新角色状态")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")  //角色修改
    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody ChangeRoleStatusDto dto) {
        return roleService.changeRoleStatus(BeanCopyUtils.copyBean(dto, Role.class));
    }

    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加角色")
    @PreAuthorize("@ps.hasPermission('system:role:add')")   //角色新增
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleDto addRoleDto) {
        return roleService.addRole(addRoleDto);
    }

    @ApiOperation("查询单个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询单个角色")
    @GetMapping("/{id}")
    public ResponseResult getRole(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @ApiOperation("更新角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新角色")
    @PreAuthorize("@ps.hasPermission('system:role:edit')")  //角色修改
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return roleService.updateRole(updateRoleDto);
    }

    @ApiOperation("删除单个角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "删除单个角色")
    @PreAuthorize("@ps.hasPermission('system:role:remove')")    //角色删除
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }

    @ApiOperation("查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询角色列表")
    @GetMapping("/listAllRole")
    public ResponseResult getRoleList() {
        return roleService.getRoleList();
    }
}
