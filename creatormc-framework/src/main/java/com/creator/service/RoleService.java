package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddRoleDto;
import com.creator.domain.dto.RoleListDto;
import com.creator.domain.dto.UpdateRoleDto;
import com.creator.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-08-06 22:20:33
 */
@SuppressWarnings("rawtypes")
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long userId);

    /**
     * 分页查询角色列表
     * @param pageNum 第几页
     * @param pageSize 一页几条记录
     * @param roleListDto
     * @return
     */
    ResponseResult getPageRoleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    /**
     * 更新角色状态
     * @param role
     * @return
     */
    ResponseResult changeRoleStatus(Role role);

    /**
     * 添加角色
     * @param addRoleDto
     * @return
     */
    ResponseResult addRole(AddRoleDto addRoleDto);

    /**
     * 查询单个角色
     * @param id 角色 id
     * @return
     */
    ResponseResult getRole(Long id);

    /**
     * 更新角色
     * @param updateRoleDto
     * @return
     */
    ResponseResult updateRole(UpdateRoleDto updateRoleDto);

    /**
     * 删除单个角色
     * @param id 角色 id
     * @return
     */
    ResponseResult deleteRole(Long id);

    /**
     * 查询角色列表
     * @return
     */
    ResponseResult getRoleList();
}

