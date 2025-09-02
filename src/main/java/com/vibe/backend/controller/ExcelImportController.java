package com.vibe.backend.controller;

import com.vibe.backend.controller.dto.TaskDto;
import com.vibe.backend.service.ExcelImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/excel")
@CrossOrigin(origins = "*")
public class ExcelImportController {

    @Autowired
    private ExcelImportService excelImportService;

    @PostMapping("/import/{estimateId}")
    public ResponseEntity<List<TaskDto>> importExcel(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long estimateId) {
        
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            String contentType = file.getContentType();
            if (contentType == null || (!contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") &&
                    !contentType.equals("application/vnd.ms-excel"))) {
                return ResponseEntity.badRequest().build();
            }

            List<TaskDto> result = excelImportService.importExcel(file, estimateId);
            return ResponseEntity.ok(result);
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
