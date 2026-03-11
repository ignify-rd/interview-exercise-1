package com.ignify.taskflow.controller;

import com.ignify.taskflow.dto.EmployeeWorkloadDto;
import com.ignify.taskflow.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/workload")
    public ResponseEntity<List<EmployeeWorkloadDto>> getWorkload() {
        return ResponseEntity.ok(reportService.getEmployeeWorkload());
    }
}
