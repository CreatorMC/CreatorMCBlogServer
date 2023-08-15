package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.CategoryListDto;
import com.creator.domain.entity.Category;

import javax.servlet.http.HttpServletResponse;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-23 16:13:26
 */
@SuppressWarnings("rawtypes")
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类表<br>
     * 分类要有已发布的文章<br>
     * 分类被禁用的话不能查出来
     * @return
     */
    ResponseResult getCategoryList();

    /**
     * 获取所有分类
     * @return
     */
    ResponseResult getAllCategory();

    /**
     * 导出分类表到Excel
     */
    void export(HttpServletResponse response);

    /**
     * 分页查询分类列表
     * @param pageNum 第几页
     * @param pageSize 每页几条记录
     * @param categoryListDto
     * @return
     */
    ResponseResult getPageCategoryList(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    /**
     * 添加分类
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);
}

