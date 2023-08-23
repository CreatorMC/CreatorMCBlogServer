package com.creator.constants;

public class COSConstants {

    /**
     * 用户头像存储位置
     */
    public static final String COS_HEAD_DIR = "head/";

    /**
     * 文章相关图片存储位置
     */
    public static final String COS_ARTICLE_COVER_DIR = "article/";

    /**
     * 头像存储大小限制<br>
     * 2MB
     */
    public static final long HEAD_SIZE_LIMIT = 2L * 1024 * 1024;

    /**
     * 文章缩略图存储大小限制
     * 2MB
     */
    public static final long ARTICLE_COVER_SIZE_LIMIT = 2L * 1024 * 1024;
}
