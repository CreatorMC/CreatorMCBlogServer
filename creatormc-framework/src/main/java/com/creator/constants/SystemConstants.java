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
     * 后台登录后redis中的key
     */
    public static final String LOGIN_ADMIN_KEY = "adminlogin:";

    /**
     * 前端请求中Header中的token的key
     */
    public static final String TOKEN_KEY = "token";

    /**
     * 根评论的评论id
     */
    public static final Long COMMENT_ROOT_ID = -1L;

    /**
     * 没有所回复的目标评论的userId
     */
    public static final Long TO_COMMENT_USER_ID_NULL = -1L;

    /**
     * 评论类型：文章
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 评论类型：友链
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 友情链接
     */
    public static final String LINK_COMMENT_TITLE = "友情链接";

    /**
     * 文章浏览量redis中的key
     */
    public static final String ARTICLE_VIEW_COUNT_KEY = "article:viewCount";

    /**
     * 文章点赞量redis中的key
     */
    public static final String ARTICLE_LIKE_COUNT_KEY = "article:likeCount";

    /**
     * 文章点赞列表redis中的key
     */
    public static final String ARTICLE_LIKE_USER_KEY = "article:likeUser";

    /**
     * 文章点赞列表的key在redis中的超时时间
     */
    public static final Long ARTICLE_LIKE_LIST_TIME = 1800L; //30分钟(单位秒)

    /**
     * 角色状态正常
     */
    public static final String ROLE_STATUS_NORMAL = "0";

    /**
     * 菜单类型为目录
     */
    public static final String MENU_TYPE_CATALOG = "M";

    /**
     * 菜单类型为菜单
     */
    public static final String MENU_TYPE_MENU = "C";

    /**
     * 菜单类型为按钮
     */
    public static final String MENU_TYPE_BUTTON = "F";

    /**
     * 菜单状态正常
     */
    public static final String MENU_STATUS_NORMAL = "0";

    /**
     * 超级管理员的roleKey
     */
    public static final String ADMIN = "admin";

    /**
     * 导出分类Excel文件的文件名
     */
    public static final String CATEGORY_EXPORT_FILENAME = "分类";

    /**
     * 导出分类Excel表格的底部名称
     */
    public static final String CATEGORY_EXPORT_SHEETNAME = "分类导出";

    /**
     * 用户类型是管理员
     */
    public static final String USER_TYPE_ADMIN = "1";

    /**
     * 用户性别为未知
     */
    public static final String USER_SEX_UNKNOWN = "2";

    /**
     * 更新菜单时父菜单id和当前菜单id相同
     */
    public static final String UPDATE_MENU_ERROR = "修改菜单'%s'失败，上级菜单不能选择自己";

    /**
     * 随机图片显示
     */
    public static final String IMAGE_RANDOM_SHOW = "1";

    /**
     * 检查是否是移动设备的UA的正则表达式
     */
    public static final String REGEX_DEVICE_IS_MOBILE = ".*(Android|iPhone|Windows Phone|iPad|iPod).*";

    /**
     * 随机图片类型是移动设备
     */
    public static final String IMAGE_RANDOM_TYPE_MOBILE = "1";

    /**
     * 随机图片类型是电脑
     */
    public static final String IMAGE_RANDOM_TYPE_PC = "0";

    /**
     * 登录过期时间（秒）
     */
    public static final Integer LOGIN_TTL = 7 * 24 * 60 * 60;  //7天
}
