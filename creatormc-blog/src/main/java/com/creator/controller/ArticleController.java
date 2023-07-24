package com.creator.controller;

import com.creator.domain.ResponseResult;
import com.creator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    @GetMapping(value = {
        "/articleList/{pageNum}/{pageSize}/{categoryId}",
        "/articleList/{pageNum}/{pageSize}"
    })
    public ResponseResult articleList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, @PathVariable(required = false) Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }
}
