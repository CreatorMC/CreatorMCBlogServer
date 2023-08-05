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
@Api(tags = "评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("获取文章评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", defaultValue = "1", paramType = "path"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1",  paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "一页几条记录", defaultValue = "10",  paramType = "path")
    })
    @SystemLog(businessName = "获取文章评论列表")
    @GetMapping("/commentList/{articleId}/{pageNum}/{pageSize}")
    public ResponseResult commentList(@PathVariable Long articleId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @ApiOperation("添加评论")
    @SystemLog(businessName = "添加评论")
    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto) {
        return commentService.addComment(BeanCopyUtils.copyBean(addCommentDto, Comment.class));
    }


    @ApiOperation("获取友链评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "path")
    })
    @SystemLog(businessName = "获取友链评论列表")
    @GetMapping("/linkCommentList/{pageNum}/{pageSize}")
    public ResponseResult linkCommentList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }
}
