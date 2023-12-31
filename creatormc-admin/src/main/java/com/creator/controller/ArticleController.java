package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddArticleDto;
import com.creator.domain.dto.UpdateArticleDto;
import com.creator.domain.entity.Article;
import com.creator.service.ArticleService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/article")
@Api("文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("添加文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加文章")
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @ApiOperation("查询文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页几条记录", defaultValue = "3", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "文章标题", defaultValue = "Spring", paramType = "query"),
            @ApiImplicitParam(name = "summary", value = "文章摘要", defaultValue = "摘要", paramType = "query")
    })
    @SystemLog(businessName = "后台查询文章列表")
    @PreAuthorize("@ps.hasPermission('content:article:query')")
    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        return articleService.getArticleList(pageNum, pageSize, title, summary);
    }

    @ApiOperation("查询文章详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "文章id", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "后台查询文章详情")
    @PreAuthorize("@ps.hasPermission('content:article:query')")
    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id) {
        return articleService.getArticleAdmin(id);
    }

    @ApiOperation("更新文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "后台更新文章")
    @PreAuthorize("@ps.hasPermission('content:article:edit')")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(BeanCopyUtils.copyBean(updateArticleDto, Article.class));
    }

    @ApiOperation("删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "删除文章")
    @PreAuthorize("@ps.hasPermission('content:article:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long ...id) {
        return articleService.deleteArticle(Arrays.asList(id));
    }
}
