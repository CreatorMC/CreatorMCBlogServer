package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.entity.Article;
import com.creator.entity.util.ResponseResult;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-07-22 17:22:07
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();
}

