package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddArticleDto;
import com.creator.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }
}
