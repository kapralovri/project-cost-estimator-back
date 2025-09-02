package com.vibe.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskExportDto {
    private Long id;
    private String stage;
    private String name;
    private boolean isRisk;
    private TaskEstimatesDto estimates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskEstimatesDto {
        private EstimateDto analysis;
        private EstimateDto frontDev;
        private EstimateDto backDev;
        private EstimateDto testing;
        private EstimateDto devops;
        private EstimateDto design;
        private EstimateDto techWriter;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstimateDto {
        private BigDecimal min;
        private BigDecimal real;
        private BigDecimal max;
    }
}
