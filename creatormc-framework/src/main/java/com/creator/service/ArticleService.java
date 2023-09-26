package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddArticleDto;
import com.creator.domain.entity.Article;

import java.util.List;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-07-22 17:22:07
 */
@SuppressWarnings("rawtypes")
public interface ArticleService extends IService<Article> {

    /**
     * 查询浏览量前10条的文章<br>
     * 需要是正式发布出来的文章<br>
     * 删除了的文章不能被查出来（因为yml里配置了逻辑删除字段，所以这里不用手动处理）
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 查询文章列表
     * @param pageNum 当前在第几页
     * @param pageSize 一页显示多少行记录
     * @param categoryId 文章类型编号（可为空）
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 查询文章详情
     * @param id 文章 id
     * @return
     */
    ResponseResult getArticle(Long id);

    /**
     * 更新对应文章的浏览量
     * @param id 文章 id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 添加文章
     * @param articleDto
     * @return
     */
    ResponseResult addArticle(AddArticleDto articleDto);

    /**
     * 后台查询文章列表
     * @param pageNum 第几页
     * @param pageSize 一页几条记录
     * @param title 标题
     * @param summary 摘要
     * @return
     */
    ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary);

    /**
     * 后台查询文章详情
     * @param id 文章 id
     * @return
     */
    ResponseResult getArticleAdmin(Long id);

    /**
     * 后台更新文章
     *
     * @param article
     * @return
     */
    ResponseResult updateArticle(Article article);

    /**
     * 删除文章
     *
     * @param ids 文章 id
     * @return
     */
    ResponseResult deleteArticle(List<Long> ids);

    /**
     * 更新对应文章的点赞量
     *
     * @param id 文章id
     * @return
     */
    ResponseResult updateLikeCount(Long id);

    /**
     * 从数据库中加载文章浏览量到redis中
     */
    void loadArticleLikeCountToRedis();

    /**
     * 查询对应文章的点赞量
     * @param id 文章id
     * @return
     */
    ResponseResult getLikeCount(Long id);

    /**
     * 查询对应文章是否被当前用户点赞过
     * @param id 文章id
     * @return
     */
    ResponseResult getUserLike(Long id);
}

