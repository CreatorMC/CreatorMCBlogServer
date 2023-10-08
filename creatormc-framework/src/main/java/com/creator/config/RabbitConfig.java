package com.creator.config;

import com.creator.constants.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue addArticleQueue() {
        return new Queue(RabbitMQConstants.ADD_ARTICLE);
    }

    @Bean
    public Queue updateArticleQueue() {
        return new Queue(RabbitMQConstants.UPDATE_ARTICLE);
    }

    @Bean
    public Queue deleteArticleQueue() {
        return new Queue(RabbitMQConstants.DELETE_ARTICLE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RabbitMQConstants.BLOG_EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeAddArticle(Queue addArticleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(addArticleQueue).to(exchange).with(RabbitMQConstants.ADD_ARTICLE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeUpdateArticle(Queue updateArticleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(updateArticleQueue).to(exchange).with(RabbitMQConstants.UPDATE_ARTICLE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeDeleteArticle(Queue deleteArticleQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deleteArticleQueue).to(exchange).with(RabbitMQConstants.DELETE_ARTICLE_ROUTING_KEY);
    }

}
