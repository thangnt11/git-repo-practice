package com.demo.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "article")
@Builder
@Data
public class Article {
    @Id
    private String id;

    private String title;

    @Field(type = FieldType.Nested, includeInParent = true)
    List<Author> authors;
}
