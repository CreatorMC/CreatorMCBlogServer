package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageCommentListDto;
import com.creator.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-07-25 22:59:16
 */
@SuppressWarnings("rawtypes")
public interface CommentService extends IService<Comment> {

    /**
     * 获取评论列表
     *
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    ResponseResult addComment(Comment comment);

    /**
     * 分页查询评论列表
     * @return
     */
    ResponseResult getPageCommentList(Integer pageNum, Integer pageSize, GetPageCommentListDto dto);
}