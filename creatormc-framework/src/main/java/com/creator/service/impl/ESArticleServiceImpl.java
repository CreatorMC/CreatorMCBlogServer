package com.creator.service.impl;

import com.creator.dao.ESArticleRepository;
import com.creator.domain.ResponseResult;
import com.creator.domain.electric.Article;
import com.creator.domain.vo.ArticleSearchVo;
import com.creator.domain.vo.PageVo;
import com.creator.service.ESArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
@Service
public class ESArticleServiceImpl implements ESArticleService {

    @Autowired
    private ESArticleRepository esArticleRepository;

    @Override
    public ResponseResult searchArticle(String text, Integer pageNum, Integer pageSize) {
        SearchPage<Article> articles = esArticleRepository.findByTitleOrSummaryOrContent(text, text, text, PageRequest.of(pageNum, pageSize));
        //封装成需要返回的对象列表
        List<ArticleSearchVo> articleSearchVos = articles.getContent().stream().map(articleSearchHit -> new ArticleSearchVo(articleSearchHit.getId(), articleSearchHit.getContent().getTitle() ,articleSearchHit.getHighlightFields())).collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(articleSearchVos, (long) articles.getTotalPages()));
    }

    @Override
    public ResponseResult saveArticle(Article article) {
        esArticleRepository.save(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticles(List<Long> ids) {
        esArticleRepository.deleteAllById(ids);
        return ResponseResult.okResult();
    }
}
