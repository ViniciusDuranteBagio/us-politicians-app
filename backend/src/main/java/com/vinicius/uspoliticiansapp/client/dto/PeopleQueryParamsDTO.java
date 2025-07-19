package com.vinicius.uspoliticiansapp.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleQueryParamsDTO {
    private Integer page;
    private Integer perPage;
    private String jurisdiction;
} 