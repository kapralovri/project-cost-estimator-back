package com.vibe.backend.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EstimateItemDto {
    private Long id;

    @NotNull
    private Long estimateId;

    @NotBlank
    private String role;

    @NotNull @Min(0)
    private BigDecimal hours;

    @NotNull @Min(0)
    private BigDecimal rate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstimateId() { return estimateId; }
    public void setEstimateId(Long estimateId) { this.estimateId = estimateId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public BigDecimal getHours() { return hours; }
    public void setHours(BigDecimal hours) { this.hours = hours; }
    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }
}


