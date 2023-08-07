package com.creator.dao;

import com.creator.domain.entity.UserRole;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-06 21:15:38
 */
@Mapper
public interface UserRoleDao extends MPJBaseMapper<UserRole> {

}

