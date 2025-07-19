package com.vinicius.uspoliticiansapp.client.dto;

import com.vinicius.uspoliticiansapp.client.enums.JurisdictionClassification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JurisdictionQueryParamsDTO {
    private Integer page;
    private Integer perPage;
    private JurisdictionClassification classification;
}