package com.vinicius.uspoliticiansapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StateDTO {
    private Long id;
    private String externalId;
    private String name;
    private LocalDateTime updatedAt;
}
