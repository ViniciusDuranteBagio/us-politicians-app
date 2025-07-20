package com.vinicius.uspoliticiansapp.controller;

import com.vinicius.uspoliticiansapp.dto.PoliticianDTO;
import com.vinicius.uspoliticiansapp.service.PoliticianService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {

    private final PoliticianService service;

    public PoliticianController(PoliticianService service) {
        this.service = service;
    }

    @GetMapping
    public List<PoliticianDTO> list(@RequestParam(name = "state") Long stateId, @RequestParam(required = false) String party)
    {
        return service.getPoliticians(stateId, party);
    }
}
