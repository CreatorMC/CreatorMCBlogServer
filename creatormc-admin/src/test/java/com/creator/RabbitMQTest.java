package com.creator;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.creator.dao.ArticleTagDao;
import com.creator.dao.ESArticleRepository;
import com.creator.domain.ResponseResult;
import com.creator.domain.entity.Article;
import com.creator.domain.entity.ArticleTag;
import com.creator.service.ArticleService;
import com.creator.service.ESArticleService;
import com.creator.utils.BeanCopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private ESArticleRepository esArticleRepository;

    @Autowired
    private ESArticleService esArticleService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagDao articleTagDao;

    /**
     * 将 Mysql 中的文章全部添加到 ElectricSearch 中
     */
    @Test
    public void initES() {
        List<Article> articles = articleService.list();
        articles.forEach(article -> {
            List<Object> list = articleTagDao.selectObjs(new LambdaQueryWrapper<ArticleTag>()
                    .select(ArticleTag::getTagId)
                    .eq(ArticleTag::getArticleId, article.getId())
            );
            List<Long> tagIds = new ArrayList<>();
            for (Object o : list) {
                tagIds.add((Long) o);
            }
            article.setTags(tagIds);
        });
        List<com.creator.domain.electric.Article> collect = articles.stream().map(article -> BeanCopyUtils.copyBean(article, com.creator.domain.electric.Article.class)).collect(Collectors.toList());
        System.out.println(esArticleRepository.saveAll(collect));
    }

    @Test
    public void searchTest() {
        ResponseResult result = esArticleService.searchArticle("文章", 0,10 );
        System.out.println(result.getData());
    }
}
