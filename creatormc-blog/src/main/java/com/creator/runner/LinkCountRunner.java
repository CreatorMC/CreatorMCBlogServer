package com.creator.runner;

import com.creator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 程序启动时查询数据库，将文章的点赞数存到Redis中
 */
@Component
public class LinkCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        articleService.loadArticleLikeCountToRedis();
    }
}
