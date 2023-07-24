package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.ArticleDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Article;
import com.creator.domain.vo.ArticleListVo;
import com.creator.domain.vo.ArticleVo;
import com.creator.domain.vo.HotArticleVo;
import com.creator.domain.vo.PageVo;
import com.creator.service.ArticleService;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 17:22:09
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    @Lazy   //防止循环注入
    private CategoryService categoryService;

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
        //将结果放进VO列表中
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                //如果 categoryId 存在，就查询指定类型的文章，如果不存在，查询全部文章
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                //文章是正式发布的
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                //置顶文章放在最前面，按照 is_top 进行降序排序
                .orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        //查询 categoryName
        List<Article> articles = page.getRecords();
        articles = articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticle(Long id) {
        Article article = getById(id);
        article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        return ResponseResult.okResult(articleVo);
    }
}

