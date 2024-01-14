package com.demo.elasticsearch.config;

import org.apache.http.HttpHost;
import com.google.common.primitives.Ints;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private static final int KEEP_ALIVE_MS = 20 * 60 * 1000;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder;
        try {
            builder = RestClient.builder(
                    new HttpHost("localhost", 9200)
            );

            builder.setHttpClientConfigCallback(
                    callback -> callback.setKeepAliveStrategy((response, context) -> KEEP_ALIVE_MS)
            );
        } catch (Exception e) {
            return new RestHighLevelClient(null);
        }
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                .build();

//        return RestClients.create(clientConfiguration).rest();
        return new RestHighLevelClient(builder);
    }

    @Bean(name = "elasticsearchRestTemplate")
    public ElasticsearchRestTemplate elasticTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}

