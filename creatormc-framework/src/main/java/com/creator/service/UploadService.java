package com.creator.service;

import com.creator.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
public interface UploadService {
    /**
     * 上传文章封面
     * @param file 封面图片
     * @return
     */
    ResponseResult uploadArticleCover(MultipartFile file);
}
