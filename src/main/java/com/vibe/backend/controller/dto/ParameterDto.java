package com.vibe.backend.controller.dto;

import java.math.BigDecimal;

public class ParameterDto {
    private Long id;
    private Long estimateId;
    private String name;
    private String value;
    private String type;
    private String description;
    private String unit;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private Boolean isRequired;
    private Integer sortOrder;

    public ParameterDto() {}

    public ParameterDto(Long id, Long estimateId, String name, String value, String type, 
                       String description, String unit, BigDecimal minValue, BigDecimal maxValue, 
                       Boolean isRequired, Integer sortOrder) {
        this.id = id;
        this.estimateId = estimateId;
        this.name = name;
        this.value = value;
        this.type = type;
        this.description = description;
        this.unit = unit;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.isRequired = isRequired;
        this.sortOrder = sortOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstimateId() { return estimateId; }
    public void setEstimateId(Long estimateId) { this.estimateId = estimateId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getMinValue() { return minValue; }
    public void setMinValue(BigDecimal minValue) { this.minValue = minValue; }
    public BigDecimal getMaxValue() { return maxValue; }
    public void setMaxValue(BigDecimal maxValue) { this.maxValue = maxValue; }
    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}

