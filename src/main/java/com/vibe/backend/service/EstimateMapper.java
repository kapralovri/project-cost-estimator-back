package com.vibe.backend.service;

import com.vibe.backend.domain.Estimate;
import com.vibe.backend.domain.EstimateItem;
import com.vibe.backend.domain.Parameter;
import com.vibe.backend.domain.Task;
import com.vibe.backend.domain.TaskEstimate;
import com.vibe.backend.controller.dto.EstimateDto;
import com.vibe.backend.controller.dto.EstimateItemDto;
import com.vibe.backend.controller.dto.ParameterDto;
import com.vibe.backend.controller.dto.TaskDto;
import com.vibe.backend.controller.dto.TaskEstimateDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class EstimateMapper {
    public static EstimateDto toDto(Estimate estimate) {
        EstimateDto dto = new EstimateDto();
        dto.setId(estimate.getId());
        dto.setProjectName(estimate.getProjectName());
        dto.setClient(estimate.getClient());
        dto.setCurrency(estimate.getCurrency());
        dto.setTotalCost(estimate.getTotalCost());
        dto.setQualityLevel(estimate.getQualityLevel());
        dto.setStatus(estimate.getStatus());
        
        List<EstimateItemDto> items = estimate.getItems().stream()
                .map(EstimateMapper::toDto)
                .collect(Collectors.toList());
        dto.setItems(items);
        
        List<ParameterDto> parameters = estimate.getParameters().stream()
                .map(EstimateMapper::toDto)
                .collect(Collectors.toList());
        dto.setParameters(parameters);
        
        List<TaskDto> tasks = estimate.getTasks().stream()
                .sorted((t1, t2) -> {
                    // Сначала по этапу (stageName)
                    int stageCompare = t1.getStageName().compareTo(t2.getStageName());
                    if (stageCompare != 0) return stageCompare;
                    
                    // Затем по названию задачи (taskName)
                    int nameCompare = t1.getTaskName().compareTo(t2.getTaskName());
                    if (nameCompare != 0) return nameCompare;
                    
                    // Наконец по ID для стабильной сортировки
                    return Long.compare(t1.getId(), t2.getId());
                })
                .map(EstimateMapper::toDto)
                .collect(Collectors.toList());
        dto.setTasks(tasks);
        
        return dto;
    }

    public static EstimateItemDto toDto(EstimateItem item) {
        EstimateItemDto dto = new EstimateItemDto();
        dto.setId(item.getId());
        dto.setEstimateId(item.getEstimate().getId());
        dto.setRole(item.getRole());
        dto.setHours(item.getHours());
        dto.setRate(item.getRate());
        return dto;
    }

    public static ParameterDto toDto(Parameter parameter) {
        ParameterDto dto = new ParameterDto();
        dto.setId(parameter.getId());
        dto.setEstimateId(parameter.getEstimate().getId());
        dto.setName(parameter.getName());
        dto.setValue(parameter.getValue());
        dto.setType(parameter.getType());
        dto.setDescription(parameter.getDescription());
        dto.setUnit(parameter.getUnit());
        dto.setMinValue(parameter.getMinValue());
        dto.setMaxValue(parameter.getMaxValue());
        dto.setIsRequired(parameter.getIsRequired());
        dto.setSortOrder(parameter.getSortOrder());
        return dto;
    }

    public static TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setEstimateId(task.getEstimate().getId());
        dto.setTaskName(task.getTaskName());
        dto.setStageName(task.getStageName());
        dto.setCategory(task.getCategory());
        dto.setComplexity(task.getComplexity());
        dto.setEstimatedHours(task.getEstimatedHours());
        dto.setActualHours(task.getActualHours());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setAssignedRole(task.getAssignedRole());
        dto.setDependencies(task.getDependencies());
        dto.setStartDate(task.getStartDate());
        dto.setDueDate(task.getDueDate());
        dto.setCompletedDate(task.getCompletedDate());
        dto.setSortOrder(task.getSortOrder());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        
        // Преобразуем оценки задач с сортировкой по роли
        List<TaskEstimateDto> estimateDtos = task.getEstimates().stream()
                .sorted((e1, e2) -> e1.getRole().compareTo(e2.getRole()))
                .map(EstimateMapper::toDto)
                .collect(Collectors.toList());
        dto.setEstimates(estimateDtos);
        
        return dto;
    }

    public static TaskEstimateDto toDto(TaskEstimate estimate) {
        TaskEstimateDto dto = new TaskEstimateDto();
        dto.setId(estimate.getId());
        dto.setTaskId(estimate.getTask().getId());
        dto.setRole(estimate.getRole());
        dto.setMin(estimate.getMin());
        dto.setReal(estimate.getReal());
        dto.setMax(estimate.getMax());
        return dto;
    }

    public static void updateTotalCost(Estimate estimate) {
        BigDecimal total = estimate.getItems().stream()
                .map(i -> i.getRate().multiply(i.getHours()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        estimate.setTotalCost(total);
    }
}


