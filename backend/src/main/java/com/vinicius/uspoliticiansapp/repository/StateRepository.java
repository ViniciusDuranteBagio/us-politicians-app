package com.vinicius.uspoliticiansapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vinicius.uspoliticiansapp.model.State;
import java.util.Optional;


public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByExternalId(String externalId);
}
