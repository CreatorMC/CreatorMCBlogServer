package com.creator.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 生成年月文件夹路径
     * @return "2023/08/"
     */
    public static String generateYearMonthFilePath() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/");
        return simpleDateFormat.format(new Date());
    }
}
