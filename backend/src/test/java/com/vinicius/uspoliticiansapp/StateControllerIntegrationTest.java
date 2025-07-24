package com.vinicius.uspoliticiansapp;

import com.vinicius.uspoliticiansapp.client.OpenStatesApiClient;
import com.vinicius.uspoliticiansapp.client.dto.JurisdictionQueryParamsDTO;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesJurisdictionsResponse;
import com.vinicius.uspoliticiansapp.client.response.OpenStatesJurisdictionsResponse.Jurisdiction;
import com.vinicius.uspoliticiansapp.model.State;
import com.vinicius.uspoliticiansapp.repository.StateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class StateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StateRepository stateRepository;

    @MockBean
    private OpenStatesApiClient openStatesApiClient;

    @BeforeEach
    public void setup() {
        stateRepository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        stateRepository.deleteAll();
    }

    @Test
    public void testAllStatesEndpointShouldCallExternalAPIAndSaveStateInDatabase() throws Exception {
        Jurisdiction jurisdiction = new Jurisdiction();
        jurisdiction.setId("mock-id");
        jurisdiction.setName("Mock State");
        OpenStatesJurisdictionsResponse mockResponse = new OpenStatesJurisdictionsResponse();
        mockResponse.setResults(List.of(jurisdiction));
        
        OpenStatesJurisdictionsResponse.Pagination pagination = new OpenStatesJurisdictionsResponse.Pagination();
        pagination.setPage(1);
        pagination.setPer_page(30);
        pagination.setMax_page(1);
        pagination.setTotal_items(1);
        mockResponse.setPagination(pagination);
        
        Mockito.when(openStatesApiClient.fetchJurisdictions(Mockito.any(JurisdictionQueryParamsDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/states/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Mock State"));

        State state = stateRepository.findAll().stream()
                .filter(s -> "mock-id".equals(s.getExternalId()))
                .findFirst()
                .orElse(null);

        assert state != null : "Mock State não foi salvo no banco de dados!";
        assert "Mock State".equals(state.getName()) : "Nome do estado salvo não confere!";
    }

    @Test
    public void testAllStatesEndpointShouldReturnDatabaseDataAndNotCallExternalAPI() throws Exception {
        State state = new State();
        state.setName("Test State");
        state.setExternalId("test-id");
        stateRepository.save(state);

        mockMvc.perform(get("/api/states/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name").value("Test State"));

        Mockito.verify(openStatesApiClient, Mockito.never()).fetchJurisdictions(Mockito.any(JurisdictionQueryParamsDTO.class));
    }
} 