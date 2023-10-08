package com.creator.mq;

import com.creator.constants.RabbitMQConstants;
import com.creator.domain.entity.Article;
import com.creator.service.ESArticleService;
import com.creator.utils.BeanCopyUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleReceiver {

    @Autowired
    private ESArticleService esArticleService;

    /**
     * 添加文章的消息
     * @param article
     */
    @RabbitListener(queues = RabbitMQConstants.ADD_ARTICLE)
    public void addArticle(Article article) {
        esArticleService.saveArticle(BeanCopyUtils.copyBean(article, com.creator.domain.electric.Article.class));
    }

    /**
     * 更新文章的消息
     * @param article
     */
    @RabbitListener(queues = RabbitMQConstants.UPDATE_ARTICLE)
    public void updateArticle(Article article) {
        esArticleService.saveArticle(BeanCopyUtils.copyBean(article, com.creator.domain.electric.Article.class));
    }

    /**
     * 删除文章的消息
     * @param ids 文章 id 列表
     */
    @RabbitListener(queues = RabbitMQConstants.DELETE_ARTICLE)
    public void deleteArticle(List<Long> ids) {
        esArticleService.deleteArticles(ids);
    }
}
