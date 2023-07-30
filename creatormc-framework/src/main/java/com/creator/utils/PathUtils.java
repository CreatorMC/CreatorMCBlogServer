package com.creator.utils;

import java.util.UUID;

public class PathUtils {

    /**
     * 用UUID生成文件名
     * @param prefix 放在哪个文件夹里
     * @param filename 原文件名
     * @return
     */
    public static String generateFilePath(String prefix, String filename) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileType = filename.substring(filename.lastIndexOf('.'));
        return prefix + uuid + fileType;
    }
}
