package com.vibe.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEstimateDto {
    private Long id;
    private Long taskId;
    private String role;
    private BigDecimal min;
    private BigDecimal real;
    private BigDecimal max;
}

