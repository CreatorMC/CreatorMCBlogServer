package com.creator.service.impl;

import com.creator.constants.COSConstants;
import com.creator.domain.ResponseResult;
import com.creator.enums.AppHttpCodeEnum;
import com.creator.exception.SystemException;
import com.creator.service.UploadService;
import com.creator.utils.COSOperate;
import com.creator.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("rawtypes")
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private COSOperate cosOperate;

    @Override
    public ResponseResult uploadArticleCover(MultipartFile file) {
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //对文件类型进行判断
        //noinspection ConstantConditions
        if(!(originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //判断文件大小
        if(file.getSize() >= COSConstants.ARTICLE_COVER_SIZE_LIMIT) {
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        String key = PathUtils.generateFilePath(COSConstants.COS_ARTICLE_COVER_DIR, originalFilename);
        //如果判断通过上传文件到COS
        return ResponseResult.okResult(cosOperate.uploadFile(key, file));
    }

    @Override
    public ResponseResult deleteArticleCover(String url) {
        String key = cosOperate.extractKey(url);
        cosOperate.deleteFile(key);
        return ResponseResult.okResult();
    }
}
