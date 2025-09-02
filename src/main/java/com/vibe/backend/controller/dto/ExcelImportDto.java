package com.vibe.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImportDto {
    private List<ExcelTaskDto> tasks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExcelTaskDto {
        private String stage;
        private String name;
        private boolean isRisk;
        private ExcelEstimatesDto estimates;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExcelEstimatesDto {
        private BigDecimal analysisMin;
        private BigDecimal analysisReal;
        private BigDecimal analysisMax;
        private BigDecimal frontDevMin;
        private BigDecimal frontDevReal;
        private BigDecimal frontDevMax;
        private BigDecimal backDevMin;
        private BigDecimal backDevReal;
        private BigDecimal backDevMax;
        private BigDecimal testingMin;
        private BigDecimal testingReal;
        private BigDecimal testingMax;
        private BigDecimal devopsMin;
        private BigDecimal devopsReal;
        private BigDecimal devopsMax;
        private BigDecimal designMin;
        private BigDecimal designReal;
        private BigDecimal designMax;
        private BigDecimal techWriterMin;
        private BigDecimal techWriterReal;
        private BigDecimal techWriterMax;
    }
}
