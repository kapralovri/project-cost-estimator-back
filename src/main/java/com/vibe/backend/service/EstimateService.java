package com.vibe.backend.service;

import com.vibe.backend.domain.Estimate;
import com.vibe.backend.domain.EstimateItem;
import com.vibe.backend.domain.Parameter;
import com.vibe.backend.domain.Task;
import com.vibe.backend.domain.TaskEstimate;
import com.vibe.backend.repository.EstimateItemRepository;
import com.vibe.backend.repository.EstimateRepository;
import com.vibe.backend.repository.ParameterRepository;
import com.vibe.backend.repository.TaskRepository;
import com.vibe.backend.repository.TaskEstimateRepository;
import com.vibe.backend.controller.dto.EstimateDto;
import com.vibe.backend.controller.dto.EstimateItemDto;
import com.vibe.backend.controller.dto.ParameterDto;
import com.vibe.backend.controller.dto.TaskDto;
import com.vibe.backend.controller.dto.TaskEstimateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EstimateService {
    private final EstimateRepository estimateRepository;
    private final EstimateItemRepository itemRepository;
    private final ParameterRepository parameterRepository;
    private final TaskRepository taskRepository;
    private final TaskEstimateRepository taskEstimateRepository;

    public EstimateService(EstimateRepository estimateRepository, EstimateItemRepository itemRepository,
                          ParameterRepository parameterRepository, TaskRepository taskRepository,
                          TaskEstimateRepository taskEstimateRepository) {
        this.estimateRepository = estimateRepository;
        this.itemRepository = itemRepository;
        this.parameterRepository = parameterRepository;
        this.taskRepository = taskRepository;
        this.taskEstimateRepository = taskEstimateRepository;
    }

    public List<Estimate> findAll() {
        return estimateRepository.findAll();
    }

    public Estimate getOrThrow(Long id) {
        return estimateRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Estimate not found: " + id));
    }

    public Estimate create(EstimateDto dto) {
        Estimate estimate = new Estimate();
        estimate.setProjectName(dto.getProjectName());
        estimate.setClient(dto.getClient());
        estimate.setCurrency(dto.getCurrency());
        if (dto.getTotalCost() != null) {
            estimate.setTotalCost(dto.getTotalCost());
        } else {
            estimate.setTotalCost(BigDecimal.ZERO);
        }
        if (dto.getQualityLevel() != null) {
            estimate.setQualityLevel(dto.getQualityLevel());
        }
        if (dto.getStatus() != null) {
            estimate.setStatus(dto.getStatus());
        }
        Estimate saved = estimateRepository.save(estimate);
        
        if (dto.getItems() != null) {
            for (EstimateItemDto itemDto : dto.getItems()) {
                addItem(saved.getId(), itemDto);
            }
        }
        
        if (dto.getParameters() != null) {
            for (ParameterDto paramDto : dto.getParameters()) {
                addParameter(saved.getId(), paramDto);
            }
        }
        
        if (dto.getTasks() != null) {
            for (TaskDto taskDto : dto.getTasks()) {
                addTask(saved.getId(), taskDto);
            }
        }
        
        return getOrThrow(saved.getId());
    }

    public Estimate update(Long id, EstimateDto dto) {
        Estimate estimate = getOrThrow(id);
        estimate.setProjectName(dto.getProjectName());
        estimate.setClient(dto.getClient());
        estimate.setCurrency(dto.getCurrency());
        if (dto.getTotalCost() != null) {
            estimate.setTotalCost(dto.getTotalCost());
        } else {
            EstimateMapper.updateTotalCost(estimate);
        }
        if (dto.getQualityLevel() != null) {
            estimate.setQualityLevel(dto.getQualityLevel());
        }
        if (dto.getStatus() != null) {
            estimate.setStatus(dto.getStatus());
        }
        // Пересоздаем параметры, если пришли
        if (dto.getParameters() != null) {
            parameterRepository.deleteByEstimateId(id);
            estimate.getParameters().clear();
            for (ParameterDto paramDto : dto.getParameters()) {
                addParameter(id, paramDto);
            }
        }

        // Пересоздаем задачи с оценками, если пришли
        if (dto.getTasks() != null) {
            // Удаляем все задачи для оценки. Каскад/ON DELETE CASCADE удалит оценки задач
            taskRepository.deleteByEstimateId(id);
            estimate.getTasks().clear();
            for (TaskDto taskDto : dto.getTasks()) {
                addTask(id, taskDto);
            }
        }

        return getOrThrow(id);
    }

    public void delete(Long id) {
        estimateRepository.deleteById(id);
    }

    public EstimateItem addItem(Long estimateId, EstimateItemDto dto) {
        Estimate estimate = getOrThrow(estimateId);
        EstimateItem item = new EstimateItem();
        item.setEstimate(estimate);
        item.setRole(dto.getRole());
        item.setHours(dto.getHours());
        item.setRate(dto.getRate());
        item.setCost(dto.getRate().multiply(dto.getHours()));
        EstimateItem saved = itemRepository.save(item);
        estimate.getItems().add(saved);
        EstimateMapper.updateTotalCost(estimate);
        return saved;
    }

    public void removeItem(Long estimateId, Long itemId) {
        Estimate estimate = getOrThrow(estimateId);
        itemRepository.deleteById(itemId);
        estimate.getItems().removeIf(i -> i.getId().equals(itemId));
        EstimateMapper.updateTotalCost(estimate);
    }

    public Parameter addParameter(Long estimateId, ParameterDto dto) {
        Estimate estimate = getOrThrow(estimateId);
        Parameter parameter = new Parameter();
        parameter.setEstimate(estimate);
        parameter.setName(dto.getName());
        parameter.setValue(dto.getValue());
        parameter.setType(dto.getType() != null ? dto.getType() : "string");
        parameter.setDescription(dto.getDescription());
        parameter.setUnit(dto.getUnit());
        parameter.setMinValue(dto.getMinValue());
        parameter.setMaxValue(dto.getMaxValue());
        parameter.setIsRequired(dto.getIsRequired() != null ? dto.getIsRequired() : false);
        parameter.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        Parameter saved = parameterRepository.save(parameter);
        estimate.getParameters().add(saved);
        return saved;
    }

    public void removeParameter(Long estimateId, Long parameterId) {
        Estimate estimate = getOrThrow(estimateId);
        parameterRepository.deleteById(parameterId);
        estimate.getParameters().removeIf(p -> p.getId().equals(parameterId));
    }

    public Task addTask(Long estimateId, TaskDto dto) {
        Estimate estimate = getOrThrow(estimateId);
        Task task = new Task();
        task.setEstimate(estimate);
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCategory(dto.getCategory() != null ? dto.getCategory() : "development");
        task.setComplexity(dto.getComplexity() != null ? dto.getComplexity() : "medium");
        task.setEstimatedHours(dto.getEstimatedHours() != null ? dto.getEstimatedHours() : BigDecimal.ZERO);
        task.setActualHours(dto.getActualHours());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : "planned");
        task.setPriority(dto.getPriority() != null ? dto.getPriority() : "medium");
        task.setAssignedRole(dto.getAssignedRole());
        task.setDependencies(dto.getDependencies());
        task.setStartDate(dto.getStartDate());
        task.setDueDate(dto.getDueDate());
        task.setCompletedDate(dto.getCompletedDate());
        task.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        
        Task saved = taskRepository.save(task);
        
        // Сохраняем оценки задач
        if (dto.getEstimates() != null) {
            for (TaskEstimateDto estimateDto : dto.getEstimates()) {
                TaskEstimate taskEstimate = new TaskEstimate();
                taskEstimate.setTask(saved);
                taskEstimate.setRole(estimateDto.getRole());
                taskEstimate.setMin(estimateDto.getMin() != null ? estimateDto.getMin() : BigDecimal.ZERO);
                taskEstimate.setReal(estimateDto.getReal() != null ? estimateDto.getReal() : BigDecimal.ZERO);
                taskEstimate.setMax(estimateDto.getMax() != null ? estimateDto.getMax() : BigDecimal.ZERO);
                taskEstimateRepository.save(taskEstimate);
            }
        }
        
        estimate.getTasks().add(saved);
        return saved;
    }

    public void removeTask(Long estimateId, Long taskId) {
        Estimate estimate = getOrThrow(estimateId);
        // Удаляем оценки задач
        taskEstimateRepository.deleteByTaskId(taskId);
        // Удаляем задачу
        taskRepository.deleteById(taskId);
        estimate.getTasks().removeIf(t -> t.getId().equals(taskId));
    }

    public TaskEstimate updateTaskEstimate(Long estimateId, Long taskId, String role, TaskEstimateDto dto) {
        // Проверяем что оценка существует
        Estimate estimate = getOrThrow(estimateId);
        Task task = estimate.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        
        // Ищем существующую оценку или создаем новую
        TaskEstimate taskEstimate = taskEstimateRepository.findByTaskIdAndRole(taskId, role)
                .orElseGet(() -> {
                    TaskEstimate newEstimate = new TaskEstimate();
                    newEstimate.setTask(task);
                    newEstimate.setRole(role);
                    return newEstimate;
                });
        
        // Обновляем значения
        if (dto.getMin() != null) {
            taskEstimate.setMin(dto.getMin());
        }
        if (dto.getReal() != null) {
            taskEstimate.setReal(dto.getReal());
        }
        if (dto.getMax() != null) {
            taskEstimate.setMax(dto.getMax());
        }
        
        return taskEstimateRepository.save(taskEstimate);
    }

    public Task updateTask(Long estimateId, Long taskId, TaskDto dto) {
        // Проверяем что задача существует
        Estimate estimate = getOrThrow(estimateId);
        Task task = estimate.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        
        // Обновляем поля задачи
        if (dto.getName() != null) {
            task.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            task.setCategory(dto.getCategory());
        }
        if (dto.getComplexity() != null) {
            task.setComplexity(dto.getComplexity());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getPriority() != null) {
            task.setPriority(dto.getPriority());
        }
        if (dto.getSortOrder() != null) {
            task.setSortOrder(dto.getSortOrder());
        }
        
        // Обновляем оценки задач если пришли
        if (dto.getEstimates() != null) {
            // Удаляем старые оценки
            taskEstimateRepository.deleteByTaskId(taskId);
            
            // Очищаем коллекцию оценок в задаче
            task.getEstimates().clear();
            
            // Создаем новые оценки
            for (TaskEstimateDto estimateDto : dto.getEstimates()) {
                TaskEstimate taskEstimate = new TaskEstimate();
                taskEstimate.setTask(task);
                taskEstimate.setRole(estimateDto.getRole());
                taskEstimate.setMin(estimateDto.getMin() != null ? estimateDto.getMin() : BigDecimal.ZERO);
                taskEstimate.setReal(estimateDto.getReal() != null ? estimateDto.getReal() : BigDecimal.ZERO);
                taskEstimate.setMax(estimateDto.getMax() != null ? estimateDto.getMax() : BigDecimal.ZERO);
                TaskEstimate savedEstimate = taskEstimateRepository.save(taskEstimate);
                task.getEstimates().add(savedEstimate);
            }
        }
        
        return taskRepository.save(task);
    }
}


