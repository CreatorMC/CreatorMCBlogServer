package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.ArticleService;
import com.creator.service.ESArticleService;
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

    @Autowired
    private ESArticleService esArticleService;

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

    @ApiOperation("更新对应文章的点赞量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "更新对应文章的点赞量")
    @PutMapping("/updateLikeCount/{id}")
    public ResponseResult updateLikeCount(@PathVariable Long id) {
        return articleService.updateLikeCount(id);
    }

    @ApiOperation("查询对应文章的点赞量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "查询对应文章的点赞量")
    @GetMapping("/getLikeCount/{id}")
    public ResponseResult getLikeCount(@PathVariable Long id) {
        return articleService.getLikeCount(id);
    }

    @ApiOperation("查询对应文章是否被当前用户点赞过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path")
    })
    @SystemLog(businessName = "查询对应文章是否被当前用户点赞过")
    @GetMapping("/getUserLike/{id}")
    public ResponseResult getUserLike(@PathVariable Long id) {
        return articleService.getUserLike(id);
    }

    @ApiOperation("搜索文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "第几页（是从0开始的）", defaultValue = "0", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "path"),
            @ApiImplicitParam(name = "text", value = "要搜索的文本", defaultValue = "文本", paramType = "path")
    })
    @SystemLog(businessName = "搜索文章")
    @GetMapping("/getSearchArticle/{pageNum}/{pageSize}/{text}")
    public ResponseResult getSearchArticle(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable String text) {
        return esArticleService.searchArticle(text, pageNum, pageSize);
    }
}
