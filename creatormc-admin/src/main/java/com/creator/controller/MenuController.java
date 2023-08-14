package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.MenuDto;
import com.creator.domain.dto.MenuListDto;
import com.creator.domain.dto.UpdateMenuDto;
import com.creator.domain.entity.Menu;
import com.creator.service.MenuService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/system/menu")
@Api("菜单")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查询菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "后台查询菜单列表")
    @GetMapping("/list")
    public ResponseResult getMenuList(MenuListDto menuListDto) {
        return menuService.getMenuList(menuListDto);
    }

    @ApiOperation("添加菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加菜单")
    @PostMapping
    public ResponseResult addMenu(@RequestBody MenuDto menuDto) {
        return menuService.addMenu(BeanCopyUtils.copyBean(menuDto, Menu.class));
    }

    @ApiOperation("查询单个菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询单个菜单")
    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id) {
        return menuService.getMenu(id);
    }

    @ApiOperation("更新菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新菜单")
    @PutMapping
    public ResponseResult updateMenu(@RequestBody UpdateMenuDto updateMenuDto) {
        return menuService.updateMenu(BeanCopyUtils.copyBean(updateMenuDto, Menu.class));
    }

    @ApiOperation("删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "删除菜单")
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }

    @ApiOperation("查询菜单树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询菜单树")
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree() {
        return menuService.getMenuTree();
    }

}
