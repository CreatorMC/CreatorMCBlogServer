package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.TagListDto;
import com.creator.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/content/tag")
@Api("标签")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("查询标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页几条记录", defaultValue = "1", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", value = "标签名", paramType = "query", required = false),
            @ApiImplicitParam(name = "remark", value = "标签备注", paramType = "query", required = false)
    })
    @SystemLog(businessName = "查询标签列表")
    @GetMapping("/list")
    public ResponseResult getTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.getTagList(pageNum, pageSize, tagListDto);
    }

    @ApiOperation("添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加标签")
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto) {
        return tagService.adTag(tagListDto);
    }
}
