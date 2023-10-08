package com.creator.dao;

import com.creator.domain.electric.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@SuppressWarnings("ALL")
public interface ESArticleRepository extends ElasticsearchRepository<Article, Long> {
    @Highlight(fields = {
            @HighlightField(name = "title", parameters = @HighlightParameters(numberOfFragments = 1, fragmentSize = 100)),
            @HighlightField(name = "content", parameters = @HighlightParameters(numberOfFragments = 1, fragmentSize = 100)),
            @HighlightField(name = "summary", parameters = @HighlightParameters(numberOfFragments = 1, fragmentSize = 100))
    })
    SearchPage<Article> findByTitleOrSummaryOrContent(String title, String summary, String content, Pageable pageable);
}
