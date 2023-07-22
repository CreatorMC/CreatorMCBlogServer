package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.dao.ArticleDao;
import com.creator.entity.Article;
import com.creator.entity.util.ResponseResult;
import com.creator.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 17:22:09
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    /**
     * 查询浏览量前10条的文章<br>
     * 需要是正式发布出来的文章<br>
     * 删除了的文章不能被查出来（因为yml里配置了逻辑删除字段，所以这里不用手动处理）
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询正式发布出来的文章，而不是草稿
        queryWrapper.eq(Article::getStatus, 0);
        //根据浏览量降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //查询前10条数据
        Page<Article> page = new Page<>(1, 10);
        page(page, queryWrapper);
        //获取查询到的结果
        List<Article> records = page.getRecords();
        return ResponseResult.okResult(records);
    }
}

