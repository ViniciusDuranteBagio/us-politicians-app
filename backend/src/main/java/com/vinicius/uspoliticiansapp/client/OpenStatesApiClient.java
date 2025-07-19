package com.vinicius.uspoliticiansapp.client;

import com.vinicius.uspoliticiansapp.client.dto.JurisdictionQueryParamsDTO;
import com.vinicius.uspoliticiansapp.client.dto.PeopleQueryParamsDTO;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesJurisdictionsResponse;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesPeopleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenStatesApiClient {
    private final WebClient webClient;

    public OpenStatesApiClient(WebClient openStatesWebClient) {
        this.webClient = openStatesWebClient;
    }

    public OpenStatesJurisdictionsResponse fetchJurisdictions(JurisdictionQueryParamsDTO params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/jurisdictions");
        
        if (params.getPage() != null) {
            builder.queryParam("page", params.getPage());
        }
        
        if (params.getPerPage() != null) {
            builder.queryParam("per_page", params.getPerPage());
        }
        
        if (params.getClassification() != null) {
            builder.queryParam("classification", params.getClassification().getValue());
        }

        String uri = builder.toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(OpenStatesJurisdictionsResponse.class)
                .block();
    }

    public OpenStatesPeopleResponse fetchPeople(PeopleQueryParamsDTO params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/people");
        
        if (params.getPage() != null) {
            builder.queryParam("page", params.getPage());
        }
        
        if (params.getPerPage() != null) {
            builder.queryParam("per_page", params.getPerPage());
        }
        
        if (params.getJurisdiction() != null && !params.getJurisdiction().trim().isEmpty()) {
            builder.queryParam("jurisdiction", params.getJurisdiction());
        }
        


        String uri = builder.toUriString();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(OpenStatesPeopleResponse.class)
                .block();
    }
}
