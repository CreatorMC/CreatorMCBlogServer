package com.creator.dao;

import com.creator.domain.entity.RoleMenu;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-07 09:44:38
 */
@Mapper
public interface RoleMenuDao extends MPJBaseMapper<RoleMenu> {

}

