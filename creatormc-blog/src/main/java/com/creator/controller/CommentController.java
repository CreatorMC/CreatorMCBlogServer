package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.constants.SystemConstants;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Comment;
import com.creator.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList/{articleId}/{pageNum}/{pageSize}")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult commentList(@PathVariable Long articleId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping
    @SystemLog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList/{pageNum}/{pageSize}")
    @SystemLog(businessName = "获取评论列表")
    public ResponseResult linkCommentList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
