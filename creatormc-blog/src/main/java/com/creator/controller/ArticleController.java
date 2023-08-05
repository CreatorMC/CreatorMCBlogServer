package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/article")
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("查询浏览量前10条的文章")
    @SystemLog(businessName = "查询浏览量前10条的文章")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @ApiOperation("查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "path"),
            @ApiImplicitParam(name = "categoryId", value = "分类id（可选）", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "查询文章列表")
    @GetMapping(value = {
        "/articleList/{pageNum}/{pageSize}/{categoryId}",
        "/articleList/{pageNum}/{pageSize}"
    })
    public ResponseResult articleList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable(required = false) Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @ApiOperation("查询文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "查询文章详情")
    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @ApiOperation("更新对应文章的浏览量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "更新对应文章的浏览量")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id) {
        return articleService.updateViewCount(id);
    }
}
