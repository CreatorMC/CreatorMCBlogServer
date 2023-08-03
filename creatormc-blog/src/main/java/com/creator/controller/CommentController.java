package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.constants.SystemConstants;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddCommentDto;
import com.creator.domain.entity.Comment;
import com.creator.service.CommentService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
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
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto) {
        return commentService.addComment(BeanCopyUtils.copyBean(addCommentDto, Comment.class));
    }

    @GetMapping("/linkCommentList/{pageNum}/{pageSize}")
    @SystemLog(businessName = "获取评论列表")
    @ApiOperation(value = "评论列表", notes = "获取评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录")
    })
    public ResponseResult linkCommentList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
