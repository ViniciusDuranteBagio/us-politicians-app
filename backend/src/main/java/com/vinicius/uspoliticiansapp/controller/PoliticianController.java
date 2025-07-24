package com.vinicius.uspoliticiansapp.controller;

import com.vinicius.uspoliticiansapp.dto.PaginatedApiResponseDTO;
import com.vinicius.uspoliticiansapp.dto.PoliticianDTO;
import com.vinicius.uspoliticiansapp.service.PoliticianService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {

    private final PoliticianService service;

    public PoliticianController(PoliticianService service) {
        this.service = service;
    }

    @GetMapping
    public PaginatedApiResponseDTO<PoliticianDTO> list(
            @RequestParam(required = false, name = "state") Long stateId,
            @RequestParam(required = false) String party,
            @RequestParam(required = false, defaultValue = "1") int currentPage,
            @RequestParam(required = false, defaultValue = "30") int perPage
    ) {
        List<PoliticianDTO> politicians = service.getPoliticians(stateId, party);
        return PaginatedApiResponseDTO.success(politicians, currentPage, perPage);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshPoliticians() {
        service.refreshAllPoliticiansFromApi();
        return ResponseEntity.ok().build();
    }
}
