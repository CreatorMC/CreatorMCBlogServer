package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddCategoryDto;
import com.creator.domain.dto.ChangeCategoryStatusDto;
import com.creator.domain.dto.GetPageCategoryListDto;
import com.creator.domain.dto.UpdateCategoryDto;
import com.creator.domain.entity.Category;
import com.creator.service.CategoryService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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

    @ApiOperation("分页查询分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "query")
    })
    @SystemLog(businessName = "分页查询分类列表")
    @GetMapping("/list")
    public ResponseResult getPageCategoryList(Integer pageNum, Integer pageSize, GetPageCategoryListDto dto) {
        return categoryService.getPageCategoryList(pageNum, pageSize, dto);
    }

    @ApiOperation("添加分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加分类")
    @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto) {
        return categoryService.addCategory(BeanCopyUtils.copyBean(addCategoryDto, Category.class));
    }

    @ApiOperation("查询单个分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询单个分类")
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @ApiOperation("更新分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新分类")
    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto) {
        return categoryService.updateCategory(BeanCopyUtils.copyBean(updateCategoryDto, Category.class));
    }

    @ApiOperation("删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "删除分类")
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long ...id) {
        return categoryService.deleteCategory(Arrays.asList(id));
    }

    @ApiOperation("更新分类状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新分类状态")
    @PutMapping("/changeStatus")
    public ResponseResult changeCategoryStatus(@RequestBody ChangeCategoryStatusDto dto) {
        return categoryService.changeCategoryStatus(BeanCopyUtils.copyBean(dto, Category.class));
    }
}
