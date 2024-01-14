package com.demo.elasticsearch.repository;

import com.demo.elasticsearch.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
    Page<Article> findByTitle(String name, Pageable pageable);

//    List<Article> findAll();
}
