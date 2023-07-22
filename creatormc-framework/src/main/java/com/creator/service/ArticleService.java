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

    /**
     * 查询浏览量前10条的文章<br>
     * 需要是正式发布出来的文章<br>
     * 删除了的文章不能被查出来（因为yml里配置了逻辑删除字段，所以这里不用手动处理）
     * @return
     */
    ResponseResult hotArticleList();
}

