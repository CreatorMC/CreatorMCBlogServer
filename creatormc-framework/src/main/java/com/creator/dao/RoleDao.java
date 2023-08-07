package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-06 22:20:33
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

}

