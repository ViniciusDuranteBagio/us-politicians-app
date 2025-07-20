package com.vinicius.uspoliticiansapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PoliticianDTO {
    private Long id;
    private String externalId;
    private String name;
    private String party;
    private String role;
    private String stateName;
    private String photoUrl;
    private LocalDateTime updatedAt;
}
