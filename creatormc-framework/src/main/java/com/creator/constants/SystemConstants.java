package com.creator.constants;

public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章是发布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 当前在第一页
     */
    public static final int PAGE_CURRENT_ONE = 1;

    /**
     * 每页显示十行记录
     */
    public static final int PAGE_SIZE_TEN = 10;

    /**
     * 分类处于正常状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";

    /**
     * 友链审核通过
     */
    public static final int LINK_STATUS_NORMAL = 0;

    /**
     * 前台博客登录后redis中的Key
     */
    public static final String LOGIN_BLOG_KEY = "bloglogin:";

    /**
     * 前端请求中Header中的token的key
     */
    public static final String TOKEN_KEY = "token";
}
