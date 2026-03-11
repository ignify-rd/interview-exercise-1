package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.DepartmentDto;
import com.ignify.taskflow.model.Department;
import com.ignify.taskflow.repository.DepartmentRepository;
import com.ignify.taskflow.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public DepartmentDto findById(Long id) {
        return departmentRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Department not found: " + id));
    }

    private DepartmentDto toDto(Department dept) {
        return new DepartmentDto(
                dept.getId(),
                dept.getName(),
                dept.getDescription(),
                (int) employeeRepository.countByDepartmentId(dept.getId())
        );
    }
}
