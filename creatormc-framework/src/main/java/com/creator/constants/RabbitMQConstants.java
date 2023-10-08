package com.creator.constants;

public class RabbitMQConstants {

    /**
     * 处理添加文章的队列名
     */
    public static final String ADD_ARTICLE = "czzmc_add_article";
    /**
     * 处理更新文章的队列名
     */
    public static final String UPDATE_ARTICLE = "czzmc_update_article";
    /**
     * 处理删除文章的队列名
     */
    public static final String DELETE_ARTICLE = "czzmc_delete_article";
    /**
     * 处理博客消息的交换机名称
     */
    public static final String BLOG_EXCHANGE = "czzmc.blog.topic";
    /**
     * 交换机与添加文章队列的 Routing key
     */
    public static final String ADD_ARTICLE_ROUTING_KEY = "admin.add.article";
    /**
     * 交换机与更新文章队列的 Routing key
     */
    public static final String UPDATE_ARTICLE_ROUTING_KEY = "admin.update.article";
    /**
     * 交换机与删除文章队列的 Routing key
     */
    public static final String DELETE_ARTICLE_ROUTING_KEY = "admin.delete.article";
}
