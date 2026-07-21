package com.ignify.taskflow.controller;

import com.ignify.taskflow.dto.EmployeeDto;
import com.ignify.taskflow.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll(
            @RequestParam(required = false) Long departmentId) {
        List<EmployeeDto> result = (departmentId != null)
                ? employeeService.findByDepartment(departmentId)
                : employeeService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }
}
