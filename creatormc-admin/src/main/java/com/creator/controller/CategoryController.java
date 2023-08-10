package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/category")
@Api("文章分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @SystemLog(businessName = "查询所有分类")
    @GetMapping("/listALLCategory")
    public ResponseResult getAllCategory() {
        return categoryService.getAllCategory();
    }
}
