package com.vibe.backend.controller;

import com.vibe.backend.domain.Estimate;
import com.vibe.backend.domain.EstimateItem;
import com.vibe.backend.domain.Parameter;
import com.vibe.backend.domain.Task;
import com.vibe.backend.service.EstimateMapper;
import com.vibe.backend.service.EstimateService;
import com.vibe.backend.controller.dto.EstimateDto;
import com.vibe.backend.controller.dto.EstimateItemDto;
import com.vibe.backend.controller.dto.ParameterDto;
import com.vibe.backend.controller.dto.TaskDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/estimates")
public class EstimateController {
    private final EstimateService estimateService;

    public EstimateController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }

    @GetMapping
    public List<EstimateDto> list() {
        return estimateService.findAll().stream().map(EstimateMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<EstimateDto> create(@RequestBody @Valid EstimateDto dto) {
        Estimate created = estimateService.create(dto);
        return ResponseEntity.created(URI.create("/api/estimates/" + created.getId()))
                .body(EstimateMapper.toDto(created));
    }

    @GetMapping("/{id}")
    public EstimateDto get(@PathVariable Long id) {
        return EstimateMapper.toDto(estimateService.getOrThrow(id));
    }

    @PutMapping("/{id}")
    public EstimateDto update(@PathVariable Long id, @RequestBody @Valid EstimateDto dto) {
        return EstimateMapper.toDto(estimateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        estimateService.delete(id);
    }

    @PostMapping("/{id}/items")
    public EstimateItemDto addItem(@PathVariable Long id, @RequestBody @Valid EstimateItemDto dto) {
        EstimateItem item = estimateService.addItem(id, dto);
        EstimateItemDto out = new EstimateItemDto();
        out.setId(item.getId());
        out.setEstimateId(id);
        out.setRole(item.getRole());
        out.setHours(item.getHours());
        out.setRate(item.getRate());
        return out;
    }

    @DeleteMapping("/{estimateId}/items/{itemId}")
    public void removeItem(@PathVariable Long estimateId, @PathVariable Long itemId) {
        estimateService.removeItem(estimateId, itemId);
    }

    @PostMapping("/{id}/parameters")
    public ParameterDto addParameter(@PathVariable Long id, @RequestBody @Valid ParameterDto dto) {
        Parameter parameter = estimateService.addParameter(id, dto);
        return EstimateMapper.toDto(parameter);
    }

    @DeleteMapping("/{estimateId}/parameters/{parameterId}")
    public void removeParameter(@PathVariable Long estimateId, @PathVariable Long parameterId) {
        estimateService.removeParameter(estimateId, parameterId);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto addTask(@PathVariable Long id, @RequestBody @Valid TaskDto dto) {
        Task task = estimateService.addTask(id, dto);
        return EstimateMapper.toDto(task);
    }

    @DeleteMapping("/{estimateId}/tasks/{taskId}")
    public void removeTask(@PathVariable Long estimateId, @PathVariable Long taskId) {
        estimateService.removeTask(estimateId, taskId);
    }
}


