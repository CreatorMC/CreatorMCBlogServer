package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/category")
@Api("文章分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询所有分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询所有分类")
    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategory() {
        return categoryService.getAllCategory();
    }

    @ApiOperation("导出所有分类到Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "导出所有分类到Excel")
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        categoryService.export(response);
    }
}
