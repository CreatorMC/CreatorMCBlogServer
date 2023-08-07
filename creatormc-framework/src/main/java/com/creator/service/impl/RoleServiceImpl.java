package com.creator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.RoleDao;
import com.creator.dao.UserRoleDao;
import com.creator.domain.entity.Role;
import com.creator.domain.entity.UserRole;
import com.creator.service.RoleService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-08-06 22:20:33
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<String> selectRoleKeysByUserId(Long userId) {
        return userRoleDao.selectJoinList(String.class, new MPJLambdaWrapper<UserRole>()
                .select(Role::getRoleKey)
                .innerJoin(Role.class, Role::getId, UserRole::getRoleId)
                .eq(UserRole::getUserId, userId)
                //角色状态为正常
                .eq(Role::getStatus, SystemConstants.ROLE_STATUS_NORMAL)
        );
    }
}

