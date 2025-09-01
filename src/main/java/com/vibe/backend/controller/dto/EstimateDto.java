package com.vibe.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class EstimateDto {
    private Long id;

    @NotBlank
    private String projectName;

    private String client;

    @NotBlank
    private String currency;

    private BigDecimal totalCost;

    private String qualityLevel;

    private String status;

    private List<EstimateItemDto> items;

    private List<ParameterDto> parameters;

    private List<TaskDto> tasks;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getTotalCost() { return totalCost; }
    public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public String getQualityLevel() { return qualityLevel; }
    public void setQualityLevel(String qualityLevel) { this.qualityLevel = qualityLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<EstimateItemDto> getItems() { return items; }
    public void setItems(List<EstimateItemDto> items) { this.items = items; }
    public List<ParameterDto> getParameters() { return parameters; }
    public void setParameters(List<ParameterDto> parameters) { this.parameters = parameters; }
    public List<TaskDto> getTasks() { return tasks; }
    public void setTasks(List<TaskDto> tasks) { this.tasks = tasks; }
}


