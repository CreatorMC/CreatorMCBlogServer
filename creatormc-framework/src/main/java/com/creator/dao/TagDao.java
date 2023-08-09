package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.creator.domain.entity.Tag;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-09 15:23:22
 */
@Mapper
public interface TagDao extends BaseMapper<Tag> {

}

