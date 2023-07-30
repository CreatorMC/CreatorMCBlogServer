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

@SuppressWarnings({"ConstantConditions", "rawtypes"})
@Service
public class COSUploadServiceImpl implements UploadService {

    @Autowired
    private COSOperate cosOperate;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对文件类型进行判断
        if(!(originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg"))) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //判断文件大小
        if(img.getSize() >= COSConstants.HEAD_SIZE_LIMIT) {
            throw new SystemException(AppHttpCodeEnum.FILE_SIZE_ERROR);
        }
        String key = PathUtils.generateFilePath(COSConstants.COS_HEAD_DIR, originalFilename);
        //如果判断通过上传文件到COS
        String url = cosOperate.uploadFile(key, img);
        return ResponseResult.okResult(url);
    }
}
