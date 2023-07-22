package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.ArticleDao;
import com.creator.entity.Article;
import com.creator.entity.util.ResponseResult;
import com.creator.service.ArticleService;
import com.creator.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 17:22:09
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询正式发布出来的文章，而不是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //根据浏览量降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //查询前10条数据
        Page<Article> page = new Page<>(SystemConstants.PAGE_CURRENT_ONE, SystemConstants.PAGE_SIZE_TEN);
        page(page, queryWrapper);
        //获取查询到的结果
        List<Article> articles = page.getRecords();
        List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article record : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(record, vo);
            articleVos.add(vo);
        }
        return ResponseResult.okResult(articleVos);
    }
}

