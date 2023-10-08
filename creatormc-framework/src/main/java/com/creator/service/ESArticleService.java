package com.creator.service;

import com.creator.domain.ResponseResult;
import com.creator.domain.electric.Article;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface ESArticleService {

    /**
     * 搜索文章
     *
     * @param text     要搜索的文本
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult searchArticle(String text, Integer pageNum, Integer pageSize);

    /**
     * 保存文章
     * @param article
     * @return
     */
    ResponseResult saveArticle(Article article);

    /**
     * 删除多个文章
     * @param ids 文章 id 列表
     * @return
     */
    ResponseResult deleteArticles(List<Long> ids);
}
