package com.vinicius.uspoliticiansapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vinicius.uspoliticiansapp.model.Politician;

import java.util.List;

@Repository
public interface PoliticianRepository extends JpaRepository<Politician, Long> {
    List<Politician> findByParty(String party);

    List<Politician> findAllByStateId(Long stateId);

    List<Politician> findAllByStateIdAndParty(Long stateId, String party);
}
