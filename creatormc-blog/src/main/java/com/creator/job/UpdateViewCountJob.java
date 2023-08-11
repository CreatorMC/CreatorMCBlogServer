package com.creator.job;

import com.creator.constants.SystemConstants;
import com.creator.domain.entity.Article;
import com.creator.service.ArticleService;
import com.creator.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时对redis中的浏览量保存到数据库
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    /**
     * 每10分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount() {
        //获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_KEY);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.parseLong(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
