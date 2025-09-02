package com.vibe.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private Long estimateId;
    private String taskName;
    private String stageName;
    private String category;
    private String complexity;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private String status;
    private String priority;
    private String assignedRole;
    private String dependencies;
    private OffsetDateTime startDate;
    private OffsetDateTime dueDate;
    private OffsetDateTime completedDate;
    private Integer sortOrder;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<TaskEstimateDto> estimates;
}
