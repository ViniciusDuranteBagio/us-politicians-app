package com.vinicius.uspoliticiansapp.client.response;

import lombok.Data;

import java.util.List;

@Data
public class OpenStatesJurisdictionsResponse {
    private List<Jurisdiction> results;
    private Pagination pagination;

    @Data
    public static class Jurisdiction {
        private String id;
        private String name;
        private String classification;
        private String division_id;
        private String url;
    }

    @Data
    public static class Pagination {
        private int per_page;
        private int page;
        private int max_page;
        private int total_items;
    }
}