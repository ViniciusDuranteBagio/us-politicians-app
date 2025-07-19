package com.vinicius.uspoliticiansapp.client.response;

import lombok.Data;

import java.util.List;

@Data
public class OpenStatesPeopleResponse {
    private List<Person> results;
    private Pagination pagination;

    @Data
    public static class Person {
        private String id;
        private String name;
        private String party;
        private CurrentRole current_role;
        private Jurisdiction jurisdiction;
        private String image;
    }

    @Data
    public static class CurrentRole {
        private String title;
        private String org_classification;
        private String district;
        private String division_id;
    }

    @Data
    public static class Jurisdiction {
        private String id;
        private String name;
        private String classification;
    }

    @Data
    public static class Pagination {
        private int per_page;
        private int page;
        private int max_page;
        private int total_items;
    }
} 