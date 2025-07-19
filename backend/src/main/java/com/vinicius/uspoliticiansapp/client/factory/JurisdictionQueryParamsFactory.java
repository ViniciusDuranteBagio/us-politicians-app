package com.vinicius.uspoliticiansapp.client.factory;

import com.vinicius.uspoliticiansapp.client.dto.JurisdictionQueryParamsDTO;
import com.vinicius.uspoliticiansapp.client.enums.JurisdictionClassification;

public class JurisdictionQueryParamsFactory {
    
    private static final int DEFAULT_PER_PAGE = 52;
    private static final int MIN_PER_PAGE = 1;
    private static final int MAX_PER_PAGE = 52;

    public static JurisdictionQueryParamsDTO forStates(int page, int perPage) {
        return withClassification(page, perPage, JurisdictionClassification.STATE);
    }

    public static JurisdictionQueryParamsDTO forStates(int page) {
        return forStates(page, DEFAULT_PER_PAGE);
    }

    public static JurisdictionQueryParamsDTO withClassification(int page, int perPage, JurisdictionClassification classification) {
        validatePerPage(perPage);
        return JurisdictionQueryParamsDTO.builder()
                .page(page)
                .perPage(perPage)
                .classification(classification)
                .build();
    }

    public static JurisdictionQueryParamsDTO withClassification(int page, JurisdictionClassification classification) {
        return withClassification(page, DEFAULT_PER_PAGE, classification);
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