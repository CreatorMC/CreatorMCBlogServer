package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.RoleListDto;
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
}

