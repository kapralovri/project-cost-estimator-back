package com.vibe.backend.repository;

import com.vibe.backend.domain.TaskEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEstimateRepository extends JpaRepository<TaskEstimate, Long> {
    List<TaskEstimate> findByTaskId(Long taskId);
    void deleteByTaskId(Long taskId);
}
