package com.creator.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.CategoryDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Article;
import com.creator.domain.entity.Category;
import com.creator.domain.vo.CategoryAllVo;
import com.creator.domain.vo.CategoryExcelVo;
import com.creator.domain.vo.CategoryVo;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.service.ArticleService;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    public void export(HttpServletResponse response) {
        try {
            //设置响应头
            WebUtils.setDownLoadHeader(SystemConstants.CATEGORY_EXPORT_FILENAME, response);
            //获取数据
            List<Category> categoryAll = list();
            List<CategoryExcelVo> categoryExcelVos = BeanCopyUtils.copyBeanList(categoryAll, CategoryExcelVo.class);
            //写入Excel文件
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).autoCloseStream(Boolean.FALSE).sheet(SystemConstants.CATEGORY_EXPORT_SHEETNAME)
                    .doWrite(categoryExcelVos);
        } catch (IOException e) {
//            throw new RuntimeException(e);
            // 重置response
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}

