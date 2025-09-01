package com.vibe.backend.controller.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class TaskDto {
    private Long id;
    private Long estimateId;
    private String name;
    private String description;
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

    public TaskDto() {}

    public TaskDto(Long id, Long estimateId, String name, String description, String category,
                   String complexity, BigDecimal estimatedHours, BigDecimal actualHours, String status,
                   String priority, String assignedRole, String dependencies, OffsetDateTime startDate,
                   OffsetDateTime dueDate, OffsetDateTime completedDate, Integer sortOrder,
                   OffsetDateTime createdAt, OffsetDateTime updatedAt, List<TaskEstimateDto> estimates) {
        this.id = id;
        this.estimateId = estimateId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.complexity = complexity;
        this.estimatedHours = estimatedHours;
        this.actualHours = actualHours;
        this.status = status;
        this.priority = priority;
        this.assignedRole = assignedRole;
        this.dependencies = dependencies;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.completedDate = completedDate;
        this.sortOrder = sortOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.estimates = estimates;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstimateId() { return estimateId; }
    public void setEstimateId(Long estimateId) { this.estimateId = estimateId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getComplexity() { return complexity; }
    public void setComplexity(String complexity) { this.complexity = complexity; }
    public BigDecimal getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(BigDecimal estimatedHours) { this.estimatedHours = estimatedHours; }
    public BigDecimal getActualHours() { return actualHours; }
    public void setActualHours(BigDecimal actualHours) { this.actualHours = actualHours; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getAssignedRole() { return assignedRole; }
    public void setAssignedRole(String assignedRole) { this.assignedRole = assignedRole; }
    public String getDependencies() { return dependencies; }
    public void setDependencies(String dependencies) { this.dependencies = dependencies; }
    public OffsetDateTime getStartDate() { return startDate; }
    public void setStartDate(OffsetDateTime startDate) { this.startDate = startDate; }
    public OffsetDateTime getDueDate() { return dueDate; }
    public void setDueDate(OffsetDateTime dueDate) { this.dueDate = dueDate; }
    public OffsetDateTime getCompletedDate() { return completedDate; }
    public void setCompletedDate(OffsetDateTime completedDate) { this.completedDate = completedDate; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<TaskEstimateDto> getEstimates() { return estimates; }
    public void setEstimates(List<TaskEstimateDto> estimates) { this.estimates = estimates; }
}
