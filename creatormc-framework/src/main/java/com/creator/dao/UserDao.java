package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.creator.domain.entity.User;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-24 19:35:22
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

