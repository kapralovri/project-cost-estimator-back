package com.vibe.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

