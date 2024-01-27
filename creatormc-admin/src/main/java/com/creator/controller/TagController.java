package com.creator.controller;

import com.creator.funnellog.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.TagListDto;
import com.creator.domain.dto.UpdateTagDto;
import com.creator.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/tag")
@Api("标签")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("分页查询标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页几条记录", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "标签名", paramType = "query", required = false),
            @ApiImplicitParam(name = "remark", value = "标签备注", paramType = "query", required = false)
    })
    @SystemLog(businessName = "分页查询标签列表")
    @PreAuthorize("@ps.hasPermission('content:tag:query')")
    @GetMapping("/list")
    public ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.getTagList(pageNum, pageSize, tagListDto);
    }

    @ApiOperation("添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加标签")
    @PreAuthorize("@ps.hasPermission('content:tag:add')")
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        return tagService.addTag(tagListDto);
    }

    @ApiOperation("删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "标签id（变长参数）", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "删除标签")
    @PreAuthorize("@ps.hasPermission('content:tag:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long ...id) {
        return tagService.deleteTag(id);
    }

    @ApiOperation("查询标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "id", value = "标签id", defaultValue = "1", paramType = "path", required = true)
    })
    @SystemLog(businessName = "查询标签信息")
    @PreAuthorize("@ps.hasPermission('content:tag:query')")
    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    @ApiOperation("更新标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新标签信息")
    @PreAuthorize("@ps.hasPermission('content:tag:edit')")
    @PutMapping
    public ResponseResult updateTag(@RequestBody UpdateTagDto dto) {
        return tagService.updateTag(dto);
    }

    @ApiOperation("查询所有标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询所有标签")
    @PreAuthorize("@ps.hasPermission('content:tag:query')")
    @GetMapping("/listAllTag")
    public ResponseResult getAllTag() {
        return tagService.getAllTag();
    }
}
