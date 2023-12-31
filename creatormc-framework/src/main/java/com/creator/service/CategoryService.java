package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.GetPageCategoryListDto;
import com.creator.domain.entity.Category;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * @param dto
     * @return
     */
    ResponseResult getPageCategoryList(Integer pageNum, Integer pageSize, GetPageCategoryListDto dto);

    /**
     * 添加分类
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);

    /**
     * 查询单个分类
     * @param id 分类 id
     * @return
     */
    ResponseResult getCategory(Long id);

    /**
     * 更新分类
     * @param category
     * @return
     */
    ResponseResult updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param ids 分类 id
     * @return
     */
    ResponseResult deleteCategory(List<Long> ids);

    /**
     * 更新分类状态
     * @param category
     * @return
     */
    ResponseResult changeCategoryStatus(Category category);
}

