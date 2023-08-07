package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-08-06 22:20:33
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeysByUserId(Long userId);
}

