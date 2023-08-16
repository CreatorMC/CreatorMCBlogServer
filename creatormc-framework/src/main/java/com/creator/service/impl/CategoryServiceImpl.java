package com.creator.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creator.constants.SystemConstants;
import com.creator.dao.CategoryDao;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageCategoryListDto;
import com.creator.domain.entity.Article;
import com.creator.domain.entity.Category;
import com.creator.domain.vo.*;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.service.ArticleService;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import com.creator.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult getPageCategoryList(Integer pageNum, Integer pageSize, GetPageCategoryListDto dto) {
        //分页查询
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, new LambdaQueryWrapper<Category>()
                //根据分类名称进行模糊查询
                .like(StringUtils.hasText(dto.getName()), Category::getName, dto.getName())
                //根据状态进行查询
                .eq(StringUtils.hasText(dto.getStatus()), Category::getStatus, dto.getStatus())
        );
        //转换为Vo
        List<Category> categories = page.getRecords();
        List<CategoryAdminListVo> categoryAdminListVos = BeanCopyUtils.copyBeanList(categories, CategoryAdminListVo.class);
        //封装
        return ResponseResult.okResult(new PageVo(categoryAdminListVos, page.getTotal()));
    }

    @Override
    public ResponseResult addCategory(Category category) {
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        CategoryAdminVo categoryAdminVo = BeanCopyUtils.copyBean(category, CategoryAdminVo.class);
        return ResponseResult.okResult(categoryAdminVo);
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

