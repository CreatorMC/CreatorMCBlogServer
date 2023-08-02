package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "查询浏览量前10条的文章")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping(value = {
        "/articleList/{pageNum}/{pageSize}/{categoryId}",
        "/articleList/{pageNum}/{pageSize}"
    })
    @SystemLog(businessName = "查询文章列表")
    public ResponseResult articleList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable(required = false) Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "查询文章详情")
    public ResponseResult getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }
}
