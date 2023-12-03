package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageCommentListDto;
import com.creator.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/comment")
@Api("文章评论")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("分页查询评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "query")
    })
    @SystemLog(businessName = "分页查询评论列表")
    @PreAuthorize("@ps.hasPermission('content:comment:query')")
    @GetMapping("/list")
    public ResponseResult getPageCommentList(Integer pageNum, Integer pageSize, GetPageCommentListDto dto) {
        return commentService.getPageCommentList(pageNum, pageSize, dto);
    }
}
