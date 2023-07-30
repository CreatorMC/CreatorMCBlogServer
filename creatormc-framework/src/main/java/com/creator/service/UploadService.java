package com.creator.service;

import com.creator.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
public interface UploadService {
    /**
     * 上传用户头像
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
