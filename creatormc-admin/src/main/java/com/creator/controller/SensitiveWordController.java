package com.creator.controller;

import com.creator.funnellog.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.domain.dto.AddSensitiveDto;
import com.creator.domain.dto.GetPageSensitiveListDto;
import com.creator.domain.dto.UpdateSensitiveDto;
import com.creator.domain.entity.SensitiveWord;
import com.creator.service.SensitiveWordService;
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
@RequestMapping("/content/sensitive")
@Api("敏感词")
public class SensitiveWordController {

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @ApiOperation("分页查询敏感词列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true),
            @ApiImplicitParam(name = "pageNum", value = "第几页", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页几条记录", defaultValue = "10", paramType = "query")
    })
    @SystemLog(businessName = "分页查询敏感词列表")
    @PreAuthorize("@ps.hasPermission('content:sensitive:query')")
    @GetMapping("/list")
    public ResponseResult getPageSensitiveList(Integer pageNum, Integer pageSize, GetPageSensitiveListDto dto) {
        return sensitiveWordService.getPageSensitiveList(pageNum, pageSize, dto);
    }

    @ApiOperation("添加敏感词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "添加敏感词")
    @PreAuthorize("@ps.hasPermission('content:sensitive:add')")
    @PostMapping
    public ResponseResult addSensitive(@RequestBody AddSensitiveDto addSensitiveDto) {
        return sensitiveWordService.addSensitive(BeanCopyUtils.copyBean(addSensitiveDto, SensitiveWord.class));
    }

    @ApiOperation("查询单个敏感词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "查询单个敏感词")
    @PreAuthorize("@ps.hasPermission('content:sensitive:query')")
    @GetMapping("/{id}")
    public ResponseResult getSensitive(@PathVariable Long id) {
        return sensitiveWordService.getSensitive(id);
    }

    @ApiOperation("更新敏感词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "更新敏感词")
    @PreAuthorize("@ps.hasPermission('content:sensitive:edit')")
    @PutMapping
    public ResponseResult updateSensitive(@RequestBody UpdateSensitiveDto updateSensitiveDto) {
        return sensitiveWordService.updateSensitive(BeanCopyUtils.copyBean(updateSensitiveDto, SensitiveWord.class));
    }

    @ApiOperation("删除敏感词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "删除敏感词")
    @PreAuthorize("@ps.hasPermission('content:sensitive:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteSensitive(@PathVariable Long ...id) {
        return sensitiveWordService.deleteSensitive(Arrays.asList(id));
    }
}
