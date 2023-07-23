package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.creator.domain.entity.Category;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-23 16:13:26
 */
@Mapper
public interface CategoryDao extends BaseMapper<Category> {

}

