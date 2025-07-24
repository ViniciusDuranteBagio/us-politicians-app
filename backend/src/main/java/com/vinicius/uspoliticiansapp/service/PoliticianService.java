package com.vinicius.uspoliticiansapp.service;

import com.vinicius.uspoliticiansapp.client.OpenStatesApiClient;
import com.vinicius.uspoliticiansapp.client.factory.PeopleQueryParamsFactory;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesPeopleResponse;
import com.vinicius.uspoliticiansapp.dto.PoliticianDTO;
import com.vinicius.uspoliticiansapp.mapper.PersonToPoliticianMapper;
import com.vinicius.uspoliticiansapp.mapper.PoliticianMapper;
import com.vinicius.uspoliticiansapp.model.Politician;
import com.vinicius.uspoliticiansapp.model.State;
import com.vinicius.uspoliticiansapp.repository.PoliticianRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoliticianService {
    private final PoliticianRepository politicianRepository;
    private final StateService stateService;
    private final OpenStatesApiClient apiClient;

    public PoliticianService(PoliticianRepository politicianRepository, 
                           StateService stateService,
                           OpenStatesApiClient apiClient) {
        this.politicianRepository = politicianRepository;
        this.stateService = stateService;
        this.apiClient = apiClient;
    }

    public List<PoliticianDTO> getPoliticians(Long stateId, String party) {
        List<Politician> politicians;
        
        if (party != null && !party.trim().isEmpty() && stateId != null) {
            politicians = politicianRepository.findAllByStateIdAndParty(stateId, party);
        } else if (stateId == null) {
            politicians = politicianRepository.findByParty(party);
        } else {
            politicians = politicianRepository.findAllByStateId(stateId);
        }

        if (politicians.isEmpty()) {
            politicians = fetchAndSavePoliticiansFromApi(stateId, party);
        }

        return entityListToDTO(politicians);
    }

    private List<Politician> fetchAndSavePoliticiansFromApi(Long stateId, String party) {

        if (stateId == null)
        {
            throw new IllegalArgumentException("É obrigatorio selecionar um estado");
        }

        List<Politician> allPoliticians = new ArrayList<>();

        State state = stateService.findById(stateId);
        if (state == null) {
            return allPoliticians;
        }

        int page = 1;
        int perPage = 50;
        OpenStatesPeopleResponse response;

        do {
            response = apiClient.fetchPeople(PeopleQueryParamsFactory.forJurisdiction(page, perPage, state.getExternalId()));

            response.getResults().forEach(person -> {
                allPoliticians.add(PersonToPoliticianMapper.toPolitician(person, state));
            });

            page++;
        } while (page <= response.getPagination().getMax_page());

        politicianRepository.saveAll(allPoliticians);
        
        if (party != null && !party.trim().isEmpty()) {
            return allPoliticians.stream()
                    .filter(politician -> party.equalsIgnoreCase(politician.getParty()))
                    .collect(Collectors.toList());
        }
        
        return allPoliticians;
    }

    public void refreshAllPoliticiansFromApi() {
        List<State> states = stateService.getAllStateEntities();
        for (State state : states) {
            int page = 1;
            int maxPage = 1;
            do {
                var response = apiClient.fetchPeople(PeopleQueryParamsFactory.forJurisdiction(page, 50, state.getExternalId()));
                if (response.getResults() != null) {
                    for (var person : response.getResults()) {
                        Politician politician = politicianRepository.findByExternalId(person.getId())
                            .orElse(new Politician());
                        Politician mapped = PersonToPoliticianMapper.toPolitician(person, state);
                        politician.setExternalId(mapped.getExternalId());
                        politician.setName(mapped.getName());
                        politician.setParty(mapped.getParty());
                        politician.setState(mapped.getState());
                        politicianRepository.save(politician);
                    }
                }
                maxPage = response.getPagination() != null ? response.getPagination().getMax_page() : 1;
                page++;
            } while (page <= maxPage);
        }
    }

    @Scheduled(cron = "0 0 0 1 1,7 *")
    public void scheduledRefreshAllPoliticiansFromApi() {
        refreshAllPoliticiansFromApi();
    }


    private List<PoliticianDTO> entityListToDTO(List<Politician> politicians) {
        return politicians.stream()
                .map(PoliticianMapper::toDTO)
                .collect(Collectors.toList());
    }
}
