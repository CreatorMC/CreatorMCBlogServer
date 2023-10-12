package com.creator.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import java.time.Duration;

@SuppressWarnings("deprecation")
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String url;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(url)
                //https://docs.spring.io/spring-data/elasticsearch/docs/4.4.5/reference/html/#elasticsearch.clients.configuration
                //配置保持连接策略
                .withClientConfigurer(
                        RestClients.RestClientConfigurationCallback.from(clientBuilder -> {
                            clientBuilder.setKeepAliveStrategy((httpResponse, httpContext) -> Duration.ofMinutes(5).toMillis());
                            return clientBuilder;
                        }))
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
