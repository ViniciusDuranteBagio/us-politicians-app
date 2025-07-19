package com.vinicius.uspoliticiansapp.service;

import com.vinicius.uspoliticiansapp.dto.StateDTO;
import com.vinicius.uspoliticiansapp.mapper.StateMapper;
import com.vinicius.uspoliticiansapp.repository.StateRepository;
import com.vinicius.uspoliticiansapp.model.State;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateService) {
        this.stateRepository = stateService;
    }

    public List<StateDTO> getAllStates() {
        List<State> states = stateRepository.findAll();
        return states.stream().map(StateMapper::toDTO).collect(Collectors.toList());
    }
}
