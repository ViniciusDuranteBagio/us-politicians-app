package com.vinicius.uspoliticiansapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient openStatesWebClient() {
        String apiKey = System.getenv("API_KEY");
        return WebClient.builder()
                .baseUrl("https://v3.openstates.org")
                .defaultHeader("X-API-KEY", apiKey)
                .build();
    }
}
