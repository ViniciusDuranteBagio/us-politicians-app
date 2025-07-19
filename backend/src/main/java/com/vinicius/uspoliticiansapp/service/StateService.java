package com.vinicius.uspoliticiansapp.service;

import com.vinicius.uspoliticiansapp.client.OpenStatesApiClient;
import com.vinicius.uspoliticiansapp.client.factory.JurisdictionQueryParamsFactory;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesJurisdictionsResponse;
import com.vinicius.uspoliticiansapp.dto.StateDTO;
import com.vinicius.uspoliticiansapp.mapper.StateMapper;
import com.vinicius.uspoliticiansapp.repository.StateRepository;
import com.vinicius.uspoliticiansapp.model.State;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {
    private final StateRepository stateRepository;
    private final OpenStatesApiClient apiClient;

    public StateService(StateRepository stateService, OpenStatesApiClient apiClient) {
        this.stateRepository = stateService;
        this.apiClient = apiClient;
    }

    public List<StateDTO> getAllStates() {
        List<State> states = stateRepository.findAll();
        if (!states.isEmpty()) {
            return states.stream().map(StateMapper::toDTO).collect(Collectors.toList());
        }

        List<State> fetchedStates = fetchAndSaveStatesFromApi();
        return fetchedStates.stream().map(StateMapper::toDTO).collect(Collectors.toList());
    }

    private List<State> fetchAndSaveStatesFromApi() {
        List<State> allStates = new ArrayList<>();

        int page = 1;
        int perPage = 52;
        OpenStatesJurisdictionsResponse response;

        do {
            response = apiClient.fetchJurisdictions(JurisdictionQueryParamsFactory.forStates(page, perPage));

            response.getResults().forEach(jurisdiction -> {
                State state = new State();
                state.setExternalId(jurisdiction.getId());
                state.setName(jurisdiction.getName());
                allStates.add(state);
            });

            page++;
        } while (page <= response.getPagination().getMax_page());

        stateRepository.saveAll(allStates);
        return allStates;
    }
}
