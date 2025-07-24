package com.vinicius.uspoliticiansapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${openstates.api.key}")
    private String apiKey;

    @Bean
    public WebClient openStatesWebClient() {
        return WebClient.builder()
                .baseUrl("https://v3.openstates.org")
                .defaultHeader("X-API-KEY", apiKey)
                .build();
    }
}
