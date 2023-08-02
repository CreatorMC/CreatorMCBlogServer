package com.creator.runner;

import com.creator.constants.SystemConstants;
import com.creator.dao.ArticleDao;
import com.creator.domain.entity.Article;
import com.creator.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 程序启动时将数据库中的文章浏览量存到Redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articles = articleDao.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(
                        Collectors.toMap(
                                article -> article.getId().toString(),
                                //这里用Integer是因为在Redis中存Long的话是1L的形式，无法累加
                                article -> article.getViewCount().intValue()
                        ));
        //存储到Redis中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_KEY,viewCountMap);
    }
}
