package com.vibe.backend.repository;

import com.vibe.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEstimateIdOrderBySortOrderAsc(Long estimateId);
    List<Task> findByEstimateIdAndStatusOrderBySortOrderAsc(Long estimateId, String status);
    List<Task> findByEstimateIdAndCategoryOrderBySortOrderAsc(Long estimateId, String category);
    void deleteByEstimateId(Long estimateId);
}
