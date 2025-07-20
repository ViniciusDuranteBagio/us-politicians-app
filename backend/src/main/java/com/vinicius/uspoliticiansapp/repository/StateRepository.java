package com.vinicius.uspoliticiansapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vinicius.uspoliticiansapp.model.State;


public interface StateRepository extends JpaRepository<State, Long> {
    State findByName(String name);
    State findByExternalId(String externalId);
}
