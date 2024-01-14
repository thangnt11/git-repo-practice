package com.demo.elasticsearch.web;

import com.demo.elasticsearch.entity.Article;
import com.demo.elasticsearch.service.ArticleService;
import com.demo.elasticsearch.web.request.ArticleRequest;
import com.demo.elasticsearch.web.response.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleResource {
    @Autowired
    ArticleService articleService;

    @PostMapping
    ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest request) {
        var res = articleService.create(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/by-title")
    ResponseEntity<Page<ArticleResponse>> getArticlesByName(@RequestParam(value = "name") String name) {
        var res = articleService.getArticlesByName(name);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all-page")
    ResponseEntity<Page<Article>> getArticlesPage() {
        var res = articleService.getAllArticlePage();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Article>> getArticles() {
        var res = articleService.getAllArticle();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
