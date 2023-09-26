package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.ArticleUserLike;
import com.creator.domain.vo.ArticleLikeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (ArticleUserLike)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-24 21:26:09
 */
@Mapper
public interface ArticleUserLikeDao extends BaseMapper<ArticleUserLike> {

    @Select("select article_id, count(user_id) as like_count from article_user_like group by article_id")
    List<ArticleLikeVo> selectLikeCount();

}

