package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.creator.domain.entity.Link;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-24 16:03:32
 */
@Mapper
public interface LinkDao extends BaseMapper<Link> {

}

