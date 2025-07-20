package com.vinicius.uspoliticiansapp.service;

import com.vinicius.uspoliticiansapp.client.OpenStatesApiClient;
import com.vinicius.uspoliticiansapp.client.dto.PeopleQueryParamsDTO;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesPeopleResponse;
import com.vinicius.uspoliticiansapp.dto.PoliticianDTO;
import com.vinicius.uspoliticiansapp.mapper.PoliticianMapper;
import com.vinicius.uspoliticiansapp.model.Politician;
import com.vinicius.uspoliticiansapp.model.State;
import com.vinicius.uspoliticiansapp.repository.PoliticianRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        
        if (party != null && !party.trim().isEmpty()) {
            politicians = politicianRepository.findAllByStateIdAndParty(stateId, party);
        } else {
            politicians = politicianRepository.findAllByStateId(stateId);
        }

        if (politicians.isEmpty()) {
            politicians = fetchAndSavePoliticiansFromApi(stateId, party);
        }

        return EntityListToDTO(politicians);
    }

    private List<Politician> fetchAndSavePoliticiansFromApi(Long stateId, String party) {
        List<Politician> allPoliticians = new ArrayList<>();

        State state = stateService.findById(stateId);
        if (state == null) {
            return allPoliticians;
        }

        int page = 1;
        int perPage = 50;
        OpenStatesPeopleResponse response;

        do {
            // passar esse param aqui para factory
            PeopleQueryParamsDTO params = PeopleQueryParamsDTO.builder()
                    .page(page)
                    .perPage(perPage)
                    .jurisdiction(state.getExternalId())
                    .build();
            response = apiClient.fetchPeople(params);

            response.getResults().forEach(person -> {
                allPoliticians.add(PersonToPolitician(person, state));
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

    // todo isso não deve ser aqui, verificar onde deve ir
    private Politician PersonToPolitician(OpenStatesPeopleResponse.Person person, State state)
    {
        Politician politician = new Politician();
        politician.setExternalId(person.getId());
        politician.setName(person.getName());
        politician.setParty(person.getParty());
        politician.setRole(person.getCurrent_role() != null ? person.getCurrent_role().getTitle() : null);
        politician.setState(state);
        politician.setPhotoUrl(person.getImage());
        politician.setUpdatedAt(LocalDateTime.now()); // isso não deve estar aqui, deve ser controlado pelo spring ou pelo banco
        return politician;
    }

    private List<PoliticianDTO> EntityListToDTO(List<Politician> politicians)
    {
        return politicians.stream()
                .map(PoliticianMapper::toDTO)
                .collect(Collectors.toList());
    }
}
