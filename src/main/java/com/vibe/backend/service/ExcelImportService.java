package com.vibe.backend.service;

import com.vibe.backend.controller.dto.ExcelImportDto;
import com.vibe.backend.controller.dto.TaskDto;
import com.vibe.backend.controller.dto.TaskEstimateDto;
import com.vibe.backend.domain.Estimate;
import com.vibe.backend.domain.Task;
import com.vibe.backend.domain.TaskEstimate;
import com.vibe.backend.repository.EstimateRepository;
import com.vibe.backend.repository.TaskEstimateRepository;
import com.vibe.backend.repository.TaskRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExcelImportService {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskEstimateRepository taskEstimateRepository;

    @Transactional
    public List<TaskDto> importExcel(MultipartFile file, Long estimateId) throws IOException {
        Estimate estimate = estimateRepository.findById(estimateId)
                .orElseThrow(() -> new RuntimeException("Estimate not found with id: " + estimateId));

        // Удаляем старые задачи и оценки
        List<Task> existingTasks = taskRepository.findByEstimateId(estimateId);
        for (Task task : existingTasks) {
            taskEstimateRepository.deleteByTaskId(task.getId());
        }
        taskRepository.deleteByEstimateId(estimateId);

        // Парсим Excel
        List<ExcelImportDto.ExcelTaskDto> excelTasks = parseExcelFile(file.getInputStream());
        
        // Сохраняем новые данные
        List<Task> savedTasks = new ArrayList<>();
        for (ExcelImportDto.ExcelTaskDto excelTask : excelTasks) {
            Task task = new Task();
            task.setEstimate(estimate);
            task.setStageName(excelTask.getStage()); // Этап сохраняем в stageName
            task.setTaskName(excelTask.getName()); // Название задачи сохраняем в taskName
            task.setIsRisk(excelTask.isRisk());
            task.setCategory("development"); // Это категория задачи, не этап
            

            task.setComplexity("medium");
            task.setStatus("planned");
            task.setPriority("medium");
            task.setSortOrder(savedTasks.size());
            

            
            Task savedTask = taskRepository.save(task);
            savedTasks.add(savedTask);

            // Сохраняем оценки для ролей
            saveTaskEstimates(savedTask, excelTask.getEstimates());
        }

        // Возвращаем данные в формате для фронта с сортировкой
        List<TaskDto> result = convertToExportDto(savedTasks);
        
        // Сортируем по этап + задача + id
        result.sort((t1, t2) -> {
            // Сначала по этапу (stageName)
            int stageCompare = t1.getStageName().compareTo(t2.getStageName());
            if (stageCompare != 0) return stageCompare;
            
            // Затем по названию задачи (taskName)
            int nameCompare = t1.getTaskName().compareTo(t2.getTaskName());
            if (nameCompare != 0) return nameCompare;
            
            // Наконец по ID для стабильной сортировки
            return Long.compare(t1.getId(), t2.getId());
        });
        
        return result;
    }

    private List<TaskDto> convertToExportDto(List<Task> tasks) {
        List<TaskDto> result = new ArrayList<>();
        
        for (Task task : tasks) {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(task.getId());
            taskDto.setEstimateId(task.getEstimate().getId());
            taskDto.setTaskName(task.getTaskName());
            taskDto.setStageName(task.getStageName());
            taskDto.setCategory(task.getCategory());
            taskDto.setComplexity(task.getComplexity());
            taskDto.setEstimatedHours(task.getEstimatedHours());
            taskDto.setActualHours(task.getActualHours());
            taskDto.setStatus(task.getStatus());
            taskDto.setPriority(task.getPriority());
            taskDto.setAssignedRole(task.getAssignedRole());
            taskDto.setDependencies(task.getDependencies());
            taskDto.setStartDate(task.getStartDate());
            taskDto.setDueDate(task.getDueDate());
            taskDto.setCompletedDate(task.getCompletedDate());
            taskDto.setSortOrder(task.getSortOrder());
            taskDto.setCreatedAt(task.getCreatedAt());
            taskDto.setUpdatedAt(task.getUpdatedAt());
            

            

            
            // Преобразуем оценки задач с сортировкой по роли
            List<TaskEstimateDto> estimateDtos = task.getEstimates().stream()
                    .sorted((e1, e2) -> e1.getRole().compareTo(e2.getRole()))
                    .map(estimate -> new TaskEstimateDto(
                        estimate.getId(),
                        estimate.getTask().getId(),
                        estimate.getRole(),
                        estimate.getMin(),
                        estimate.getReal(),
                        estimate.getMax()
                    ))
                    .collect(Collectors.toList());
            taskDto.setEstimates(estimateDtos);
            
            result.add(taskDto);
        }
        
        return result;
    }

    private List<ExcelImportDto.ExcelTaskDto> parseExcelFile(InputStream inputStream) throws IOException {
        List<ExcelImportDto.ExcelTaskDto> tasks = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            
            // Пропускаем заголовки (первые 3 строки)
            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                String stage = getCellValueAsString(row.getCell(0)); // Колонка A - Этап
                String name = getCellValueAsString(row.getCell(1)); // Колонка B - Задача
                String riskValue = getCellValueAsString(row.getCell(3)); // Колонка D - Риск
                boolean isRisk = isRiskValue(riskValue);
                

                

                
                ExcelImportDto.ExcelEstimatesDto estimates = parseEstimates(row);
                
                if (stage != null && !stage.trim().isEmpty() && name != null && !name.trim().isEmpty()) {
                    tasks.add(new ExcelImportDto.ExcelTaskDto(stage, name, isRisk, estimates));
                }
            }
        }
        
        return tasks;
    }

    private ExcelImportDto.ExcelEstimatesDto parseEstimates(Row row) {
        ExcelImportDto.ExcelEstimatesDto estimates = new ExcelImportDto.ExcelEstimatesDto();
        
        // Анализ + документирование (колонки E, F, G)
        estimates.setAnalysisMin(getCellValueAsBigDecimal(row.getCell(4)));
        estimates.setAnalysisReal(getCellValueAsBigDecimal(row.getCell(5)));
        estimates.setAnalysisMax(getCellValueAsBigDecimal(row.getCell(6)));
        
        // Front Dev (колонки I, J, K)
        estimates.setFrontDevMin(getCellValueAsBigDecimal(row.getCell(8)));
        estimates.setFrontDevReal(getCellValueAsBigDecimal(row.getCell(9)));
        estimates.setFrontDevMax(getCellValueAsBigDecimal(row.getCell(10)));
        
        // Back Dev (колонки M, N, O)
        estimates.setBackDevMin(getCellValueAsBigDecimal(row.getCell(12)));
        estimates.setBackDevReal(getCellValueAsBigDecimal(row.getCell(13)));
        estimates.setBackDevMax(getCellValueAsBigDecimal(row.getCell(14)));
        

        
        // Остальные роли пока оставляем нулевыми, так как в шаблоне их нет
        estimates.setTestingMin(BigDecimal.ZERO);
        estimates.setTestingReal(BigDecimal.ZERO);
        estimates.setTestingMax(BigDecimal.ZERO);
        estimates.setDevopsMin(BigDecimal.ZERO);
        estimates.setDevopsReal(BigDecimal.ZERO);
        estimates.setDevopsMax(BigDecimal.ZERO);
        estimates.setDesignMin(BigDecimal.ZERO);
        estimates.setDesignReal(BigDecimal.ZERO);
        estimates.setDesignMax(BigDecimal.ZERO);
        estimates.setTechWriterMin(BigDecimal.ZERO);
        estimates.setTechWriterReal(BigDecimal.ZERO);
        estimates.setTechWriterMax(BigDecimal.ZERO);
        
        return estimates;
    }

    private void saveTaskEstimates(Task task, ExcelImportDto.ExcelEstimatesDto estimates) {
        String[] roles = {"analysis", "frontDev", "backDev", "testing", "devops", "design", "techWriter"};
        
        for (String role : roles) {
            TaskEstimate taskEstimate = new TaskEstimate();
            taskEstimate.setTask(task);
            taskEstimate.setRole(role);
            
            switch (role) {
                case "analysis":
                    taskEstimate.setMin(estimates.getAnalysisMin() != null ? estimates.getAnalysisMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getAnalysisReal() != null ? estimates.getAnalysisReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getAnalysisMax() != null ? estimates.getAnalysisMax() : BigDecimal.ZERO);
                    break;
                case "frontDev":
                    taskEstimate.setMin(estimates.getFrontDevMin() != null ? estimates.getFrontDevMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getFrontDevReal() != null ? estimates.getFrontDevReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getFrontDevMax() != null ? estimates.getFrontDevMax() : BigDecimal.ZERO);
                    break;
                case "backDev":
                    taskEstimate.setMin(estimates.getBackDevMin() != null ? estimates.getBackDevMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getBackDevReal() != null ? estimates.getBackDevReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getBackDevMax() != null ? estimates.getBackDevMax() : BigDecimal.ZERO);
                    break;
                case "testing":
                    taskEstimate.setMin(estimates.getTestingMin() != null ? estimates.getTestingMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getTestingReal() != null ? estimates.getTestingReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getTestingMax() != null ? estimates.getTestingMax() : BigDecimal.ZERO);
                    break;
                case "devops":
                    taskEstimate.setMin(estimates.getDevopsMin() != null ? estimates.getDevopsMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getDevopsReal() != null ? estimates.getDevopsReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getDevopsMax() != null ? estimates.getDevopsMax() : BigDecimal.ZERO);
                    break;
                case "design":
                    taskEstimate.setMin(estimates.getDesignMin() != null ? estimates.getDesignMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getDesignReal() != null ? estimates.getDesignReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getDesignMax() != null ? estimates.getDesignMax() : BigDecimal.ZERO);
                    break;
                case "techWriter":
                    taskEstimate.setMin(estimates.getTechWriterMin() != null ? estimates.getTechWriterMin() : BigDecimal.ZERO);
                    taskEstimate.setReal(estimates.getTechWriterReal() != null ? estimates.getTechWriterReal() : BigDecimal.ZERO);
                    taskEstimate.setMax(estimates.getTechWriterMax() != null ? estimates.getTechWriterMax() : BigDecimal.ZERO);
                    break;
            }
            
            // Сохраняем оценку
            TaskEstimate savedEstimate = taskEstimateRepository.save(taskEstimate);
            
            // Добавляем оценку в коллекцию задачи
            task.getEstimates().add(savedEstimate);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
                    try {
                        return String.valueOf((int) cell.getNumericCellValue());
                    } catch (Exception ex) {
                        return null;
                    }
                }
            default:
                return null;
        }
    }

    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                try {
                    String value = cell.getStringCellValue().trim();
                    if (value.isEmpty()) return BigDecimal.ZERO;
                    return new BigDecimal(value);
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            default:
                return BigDecimal.ZERO;
        }
    }

    private boolean isRiskValue(String value) {
        if (value == null) return false;
        String lowerValue = value.toLowerCase().trim();
        return lowerValue.equals("да") || lowerValue.equals("yes") || 
               lowerValue.equals("true") || lowerValue.equals("1");
    }
}
