package com.vinicius.uspoliticiansapp.mapper;

import com.vinicius.uspoliticiansapp.client.response.OpenStatesPeopleResponse;
import com.vinicius.uspoliticiansapp.model.Politician;
import com.vinicius.uspoliticiansapp.model.State;

import java.time.LocalDateTime;

public class PersonToPoliticianMapper {
    
    public static Politician toPolitician(OpenStatesPeopleResponse.Person person, State state) {
        Politician politician = new Politician();
        politician.setExternalId(person.getId());
        politician.setName(person.getName());
        politician.setParty(person.getParty());
        politician.setRole(person.getCurrent_role() != null ? person.getCurrent_role().getTitle() : null);
        politician.setState(state);
        politician.setPhotoUrl(person.getImage());
        politician.setUpdatedAt(LocalDateTime.now());
        return politician;
    }
} 