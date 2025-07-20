package com.vinicius.uspoliticiansapp.mapper;

import com.vinicius.uspoliticiansapp.dto.PoliticianDTO;
import com.vinicius.uspoliticiansapp.model.Politician;

public class PoliticianMapper {
    public static PoliticianDTO toDTO(Politician politicianEntity) {
        return new PoliticianDTO(
            politicianEntity.getId(),
            politicianEntity.getExternalId(),
            politicianEntity.getName(),
            politicianEntity.getParty(),
            politicianEntity.getRole(),
            politicianEntity.getState() != null ? politicianEntity.getState().getName() : null,
            politicianEntity.getPhotoUrl(),
            politicianEntity.getUpdatedAt()
        );
    }
}