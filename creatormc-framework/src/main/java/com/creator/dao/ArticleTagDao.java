package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-11 10:08:28
 */
@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}

