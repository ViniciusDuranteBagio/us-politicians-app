package com.vinicius.uspoliticiansapp.controller;

import com.vinicius.uspoliticiansapp.dto.StateDTO;
import com.vinicius.uspoliticiansapp.service.StateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/all")
    public List<StateDTO> getAllStates() {
        // TODO criar um response List DTO padrão para a listagem, onde vai ter o Data: [] e os dados da listagem: page, perPage, TotalItens
        return stateService.getAllStates();
    }
}
