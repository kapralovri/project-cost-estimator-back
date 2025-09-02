package com.vibe.backend.repository;

import com.vibe.backend.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
    List<Parameter> findByEstimateIdOrderBySortOrderAsc(Long estimateId);
    void deleteByEstimateId(Long estimateId);
}

