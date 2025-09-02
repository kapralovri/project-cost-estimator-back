package com.vibe.backend.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}


