package com.dkt.breaking.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient mapWebClient(@Value("${api.map.url}") String mapApi, @Value("${api.map.appkey}") String appKey) {
        return WebClient.builder()
                .baseUrl(mapApi)
                /*.filter(logRequest())*/
                .defaultHeader("Authorization", appKey)
                .build();
    }

    @Bean
    public WebClient localWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8021")
                /*.filter(logRequest())*/
                .build();
    }
}
