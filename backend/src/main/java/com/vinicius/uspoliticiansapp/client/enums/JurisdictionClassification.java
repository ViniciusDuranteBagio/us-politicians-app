package com.vinicius.uspoliticiansapp.client.enums;

public enum JurisdictionClassification {
    STATE("state"),
    MUNICIPALITY("municipality"),
    COUNTRY("country");

    private final String value;

    JurisdictionClassification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
} 