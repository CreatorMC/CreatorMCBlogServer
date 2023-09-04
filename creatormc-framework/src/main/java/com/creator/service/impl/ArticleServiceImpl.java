package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.ArticleDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddArticleDto;
import com.creator.domain.entity.Article;
import com.creator.domain.entity.ArticleTag;
import com.creator.domain.vo.*;
import com.creator.service.ArticleService;
import com.creator.service.ArticleTagService;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-07-22 17:22:09
 */
@SuppressWarnings("rawtypes")
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    @Lazy   //防止循环注入
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private RedisCache redisCache;

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
        //从Redis中获取浏览量并设置给Article对象
        articles = articles.stream()
                .map(article -> article.setViewCount(getViewCount(article.getId()).longValue()))
                .collect(Collectors.toList());
        //将结果放进VO列表中
        List<ArticleHotVo> articleVos = BeanCopyUtils.copyBeanList(articles, ArticleHotVo.class);
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
                .peek(article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    //从Redis中获取浏览量并设置给Article对象
                    article.setViewCount(getViewCount(article.getId()).longValue());
                })
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
        //从Redis中获取浏览量并设置给Article对象
        article.setViewCount(getViewCount(id).longValue());
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);
        return ResponseResult.okResult(articleVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新对应文章的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT_KEY, id.toString(), 1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional  //开启事务功能
    public ResponseResult addArticle(AddArticleDto articleDto) {
        //将dto转换为文章对象，存到数据库中
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
        //将文章的标签保存到数据库中
        List<ArticleTag> articleTags = articleDto.getTags().stream().map(tagId -> new ArticleTag(article.getId(),tagId)).collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
        //有标题按标题查，有摘要按摘要查，两个都没有查全部
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Article>()
                .like(StringUtils.hasText(title), Article::getTitle, title)
                .like(StringUtils.hasText(summary), Article::getSummary, summary)
        );
        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(new PageVo(BeanCopyUtils.copyBeanList(articles, ArticleAdminListVo.class), page.getTotal()));
    }

    @Override
    public ResponseResult getArticleAdmin(Long id) {
        Article article = getById(id);
        List<ArticleTag> articleTags = articleTagService.list(new LambdaQueryWrapper<ArticleTag>()
                .select(ArticleTag::getTagId)
                .eq(ArticleTag::getArticleId, article.getId())
        );
        List<Long> tags = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
        article.setTags(tags);
        return ResponseResult.okResult(article);
    }

    @Override
    @Transactional  //开启事务功能
    public ResponseResult updateArticle(Article article) {
        //更新文章表
        updateById(article);
        //删除文章与标签的关联表中的相关记录
        articleTagService.remove(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, article.getId())
        );
        //封装为ArticleTag列表
        List<ArticleTag> articleTags = article.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        //保存到文章与标签的关联表中
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    /**
     * 从Redis中返回指定的文章浏览量
     * @param id 文章id
     * @return 浏览量
     */
    private Integer getViewCount(Long id) {
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT_KEY, id.toString());
        if(Objects.isNull(viewCount)) {
            //获取不到文章浏览量，说明文章是新添加的文章，直接返回0
            viewCount = 0;
        }
        return viewCount;
    }
}

