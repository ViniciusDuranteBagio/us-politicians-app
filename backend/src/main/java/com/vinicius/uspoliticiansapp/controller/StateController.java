package com.vinicius.uspoliticiansapp.controller;

import com.vinicius.uspoliticiansapp.dto.PaginatedApiResponseDTO;
import com.vinicius.uspoliticiansapp.dto.StateDTO;
import com.vinicius.uspoliticiansapp.service.StateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public PaginatedApiResponseDTO<StateDTO> getAllStates(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "perPage", required = false, defaultValue = "30") int perPage
    ) {
        return PaginatedApiResponseDTO.success(stateService.getAllStates(), currentPage, perPage);
    }
}
