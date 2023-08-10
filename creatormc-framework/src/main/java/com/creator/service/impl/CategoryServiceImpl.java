package com.creator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.CategoryDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Article;
import com.creator.domain.entity.Category;
import com.creator.domain.vo.CategoryAllVo;
import com.creator.domain.vo.CategoryVo;
import com.creator.service.ArticleService;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-07-23 16:13:26
 */
@SuppressWarnings("rawtypes")
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toSet());
        //查询分类表，分类状态为正常
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成Vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult getAllCategory() {
        //查询正常状态下的分类
        List<Category> categoryList = list(new LambdaQueryWrapper<Category>()
                .eq(Category::getStatus, SystemConstants.CATEGORY_STATUS_NORMAL)
        );
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(categoryList, CategoryAllVo.class));
    }
}

