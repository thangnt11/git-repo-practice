package com.demo.elasticsearch.web.response;

import com.demo.elasticsearch.entity.Article;
import com.demo.elasticsearch.entity.Author;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude
public class ArticleResponse {
    private String id;
    private String title;
    private List<Author> authors;

    public static ArticleResponse fromEntity(Article article) {
        ArticleResponse res = ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .authors(article.getAuthors())
                .build();
        return res;
    }

}
