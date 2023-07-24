package com.creator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-07-23 16:13:26
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类表<br>
     * 分类要有已发布的文章<br>
     * 分类被禁用的话不能查出来
     * @return
     */
    ResponseResult getCategoryList();
}

