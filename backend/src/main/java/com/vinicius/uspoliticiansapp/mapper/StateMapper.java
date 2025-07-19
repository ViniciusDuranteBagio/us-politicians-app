package com.vinicius.uspoliticiansapp.mapper;

import com.vinicius.uspoliticiansapp.dto.StateDTO;
import com.vinicius.uspoliticiansapp.model.State;

public class StateMapper {
    public static StateDTO toDTO(State entity) {
        return new StateDTO(
            entity.getId(),
            entity.getExternalId(),
            entity.getName(),
            entity.getUpdatedAt()
        );
    }
}