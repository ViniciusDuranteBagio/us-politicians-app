package com.vinicius.uspoliticiansapp.client.factory;

import com.vinicius.uspoliticiansapp.client.dto.PeopleQueryParamsDTO;

public class PeopleQueryParamsFactory {
    
    private static final int DEFAULT_PER_PAGE = 50;
    private static final int MIN_PER_PAGE = 1;
    private static final int MAX_PER_PAGE = 50;

    public static PeopleQueryParamsDTO forJurisdiction(int page, int perPage, String jurisdiction) {
        validatePerPage(perPage);
        return PeopleQueryParamsDTO.builder()
                .page(page)
                .perPage(perPage)
                .jurisdiction(jurisdiction)
                .build();
    }

    public static PeopleQueryParamsDTO forJurisdiction(int page, String jurisdiction) {
        return forJurisdiction(page, DEFAULT_PER_PAGE, jurisdiction);
    }

    private static void validatePerPage(int perPage) {
        if (perPage < MIN_PER_PAGE || perPage > MAX_PER_PAGE) {
            throw new IllegalArgumentException(
                String.format("perPage deve estar entre %d e %d. Valor fornecido: %d", 
                    MIN_PER_PAGE, MAX_PER_PAGE, perPage)
            );
        }
    }
} 