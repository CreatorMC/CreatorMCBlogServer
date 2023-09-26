package com.creator.job;

import com.creator.constants.SystemConstants;
import com.creator.domain.entity.ArticleUserLike;
import com.creator.service.ArticleUserLikeService;
import com.creator.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定时对redis中的文章点赞信息保存到数据库
 */
@Component
public class UpdateLinkCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleUserLikeService articleUserLikeService;

    /**
     * 每10分钟执行一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional
    public void updateLinkCount() {
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.ARTICLE_LIKE_USER_KEY);
        if(Objects.isNull(cacheMap)) {
            //如果没有获取到，说明key此时不存在
            return;
        }
        List<ArticleUserLike> articleUserLikes = cacheMap.keySet().stream().map(key -> {
            String[] strings = key.split(":");
            return new ArticleUserLike(Long.parseLong(strings[1]), Long.parseLong(strings[3]));
        }).collect(Collectors.toList());
        redisCache.deleteObject(SystemConstants.ARTICLE_LIKE_USER_KEY);
        articleUserLikeService.saveBatch(articleUserLikes);
    }
}
