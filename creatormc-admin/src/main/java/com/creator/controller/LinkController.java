package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddLinkDto;
import com.creator.domain.dto.GetPageLinkListDto;
import com.creator.domain.dto.UpdateLinkDto;
import com.creator.domain.entity.Link;
import com.creator.service.LinkService;
import com.creator.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/link")
@Api("友链")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("分页查询友链列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "query")
    })
    @SystemLog(businessName = "分页查询友链列表")
    @PreAuthorize("@ps.hasPermission('content:link:query')")    //友链查询
    @GetMapping("/list")
    public ResponseResult getPageLinkList(Integer pageNum, Integer pageSize, GetPageLinkListDto dto) {
        return linkService.getPageLinkList(pageNum, pageSize, dto);
    }

    @ApiOperation("添加友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加友链")
    @PreAuthorize("@ps.hasPermission('content:link:add')")  //友链新增
    @PostMapping
    public ResponseResult addLink(@RequestBody AddLinkDto dto) {
        return linkService.addLink(BeanCopyUtils.copyBean(dto, Link.class));
    }

    @ApiOperation("查询单个友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询单个友链")
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable Long id) {
        return linkService.getLink(id);
    }

    @ApiOperation("更新友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新友链")
    @PreAuthorize("@ps.hasPermission('content:link:edit')") //友链修改
    @PutMapping
    public ResponseResult updateLink(@RequestBody UpdateLinkDto dto) {
        return linkService.updateLink(BeanCopyUtils.copyBean(dto, Link.class));
    }

    @ApiOperation("删除友链")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "友链id（可以多个）", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "删除友链")
    @PreAuthorize("@ps.hasPermission('content:link:remove')")   //友链删除
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long ...id) {
        return linkService.deleteLink(Arrays.asList(id));
    }
}
