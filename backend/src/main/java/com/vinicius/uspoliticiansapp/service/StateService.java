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
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class StateService {
    private final StateRepository stateRepository;
    private final OpenStatesApiClient apiClient;

    public StateService(StateRepository stateService, OpenStatesApiClient apiClient) {
        this.stateRepository = stateService;
        this.apiClient = apiClient;
    }

    public State findById(Long id)
    {
        return stateRepository.findById(id).orElse(null);
    }

    public List<StateDTO> getAllStates() {
        List<State> states = stateRepository.findAll();
        if (states.isEmpty()) {
            states = fetchAndSaveStatesFromApi();
        }
        return entityListToDTO(states);
    }

    private List<StateDTO> entityListToDTO(List<State> states)
    {
        return states.stream()
                .map(StateMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<State> fetchAndSaveStatesFromApi() {
        List<State> allStates = new ArrayList<>();

        int page = 1;
        OpenStatesJurisdictionsResponse response;

        do {
            response = apiClient.fetchJurisdictions(JurisdictionQueryParamsFactory.forStates(page));

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

    public void refreshStatesFromApi() {
        int page = 1;
        int maxPage = 1;
        do {
            var response = apiClient.fetchJurisdictions(JurisdictionQueryParamsFactory.forStates(page));
            if (response.getResults() != null) {
                for (var jurisdiction : response.getResults()) {
                    State state = stateRepository.findByExternalId(jurisdiction.getId())
                        .orElse(new State());
                    state.setExternalId(jurisdiction.getId());
                    state.setName(jurisdiction.getName());
                    stateRepository.save(state);
                }
            }
            maxPage = response.getPagination() != null ? response.getPagination().getMax_page() : 1;
            page++;
        } while (page <= maxPage);
    }

    @Scheduled(cron = "0 0 0 1 1,7 *")
    public void scheduledRefreshStatesFromApi() {
        refreshStatesFromApi();
    }

    public List<State> getAllStateEntities() {
        List<State> states = stateRepository.findAll();
        if (states.isEmpty()) {
            states = fetchAndSaveStatesFromApi();
        }
        return states;
    }
}
