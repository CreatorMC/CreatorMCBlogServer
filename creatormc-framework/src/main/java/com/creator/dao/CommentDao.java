package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.creator.domain.entity.Comment;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-25 22:59:16
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {

}

