package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
@RestController
@Api("文件上传")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @ApiOperation("上传文章封面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录后的token", paramType = "header", required = true)
    })
    @SystemLog(businessName = "上传文章封面")
    @PostMapping("/upload")
    public ResponseResult uploadArticleCover(@RequestParam("img") MultipartFile file) {
        return uploadService.uploadArticleCover(file);
    }
}
