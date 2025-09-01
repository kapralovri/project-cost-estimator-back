package com.vibe.backend.controller.dto;

import java.math.BigDecimal;

public class TaskEstimateDto {
    private Long id;
    private Long taskId;
    private String role;
    private BigDecimal min;
    private BigDecimal real;
    private BigDecimal max;

    public TaskEstimateDto() {}

    public TaskEstimateDto(Long id, Long taskId, String role, BigDecimal min, BigDecimal real, BigDecimal max) {
        this.id = id;
        this.taskId = taskId;
        this.role = role;
        this.min = min;
        this.real = real;
        this.max = max;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public BigDecimal getMin() { return min; }
    public void setMin(BigDecimal min) { this.min = min; }
    public BigDecimal getReal() { return real; }
    public void setReal(BigDecimal real) { this.real = real; }
    public BigDecimal getMax() { return max; }
    public void setMax(BigDecimal max) { this.max = max; }
}
