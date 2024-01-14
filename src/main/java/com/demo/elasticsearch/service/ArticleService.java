package com.demo.elasticsearch.service;

import com.demo.elasticsearch.entity.Article;
import com.demo.elasticsearch.entity.Author;
import com.demo.elasticsearch.repository.ArticleRepository;
import com.demo.elasticsearch.web.request.ArticleRequest;
import com.demo.elasticsearch.web.response.ArticleResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ArticleResponse create(ArticleRequest request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .authors(Arrays.asList(new Author("thang")))
                .build();
        var saved = articleRepository.save(article);
        return ArticleResponse.fromEntity(saved);
    }

    public Page<ArticleResponse> getArticlesByName(String name) {
        var res = articleRepository.findByTitle(name, PageRequest.of(1, 10));
        return new PageImpl<>(res.getContent().stream().map(ArticleResponse::fromEntity).collect(Collectors.toList()));
    }

    public Page<Article> getAllArticlePage() {
//        var res = articleRepository.findAll(PageRequest.of(1, 10));
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(QueryBuilders.matchAllQuery());
        searchQuery.withQuery(builder);
        try {
            SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery.build(), Article.class, IndexCoordinates.of("article"));
            SearchPage<Article> pages = SearchHitSupport.searchPageFor(searchHits, PageRequest.of(1,10));
            List<Article> rows = pages.toList().stream().map(searchHit->searchHit.getContent()).collect(Collectors.toList());
            long count = pages.getTotalElements();
            return new PageImpl<>(rows, PageRequest.of(1,10), count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Article> getAllArticle() {
//        var res = articleRepository.findAll(PageRequest.of(1, 10));
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(QueryBuilders.matchAllQuery());
        searchQuery.withQuery(builder);
        try {
            SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery.build(), Article.class, IndexCoordinates.of("article"));
            return searchHits == null ? null : searchHits.stream().map(searchHit -> searchHit.getContent()).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
