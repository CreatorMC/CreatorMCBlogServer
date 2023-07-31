package com.creator.utils;

import com.creator.config.COSConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Component
public class COSOperate {

    @Autowired
    private COSClient cosClient;

    @Autowired
    private COSConfig cosConfig;

    /**
     * 简单上传文件（大小在20MB以内）
     * @param key 存到COS中的文件名
     * @param file 接收到的文件
     * @return 文件访问地址
     */
    public String uploadFile(String key, MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // bucket 名需包含 appid
        String bucketName = cosConfig.getBucket();
        // 上传 object, 建议 20M 以下的文件使用该接口
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        objectMetadata.setContentLength(file.getSize());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        String url = "";
        try {
            cosClient.putObject(putObjectRequest);
            URL objectUrl = cosClient.getObjectUrl(bucketName, key);
            url = objectUrl.toString();
        } catch (CosServiceException e) {
            //失败，抛出 CosServiceException
            throw new RuntimeException(e);
        } catch (CosClientException e) {
            //失败，抛出 CosClientException
            throw new RuntimeException(e);
        }
        // 关闭客户端
        // 因为COSClient是线程安全的类，还可以让其他线程访问，所以不能关闭
        // cosClient.shutdown();
        return url;
    }

    /**
     * 删除文件
     * @param key 文件在COS中的Key
     * @return
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public void deleteFile(String key) {
        try {
            cosClient.deleteObject(cosConfig.getBucket(), key);
        } catch (CosServiceException e) {
            throw new RuntimeException(e);
        } catch (CosClientException e) {
            throw new RuntimeException(e);
        }
    }
}
