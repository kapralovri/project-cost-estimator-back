package com.vibe.backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}


