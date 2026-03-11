package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.EmployeeDto;
import com.ignify.taskflow.model.Employee;
import com.ignify.taskflow.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAllWithDepartment().stream()
                .map(this::toDto)
                .toList();
    }

    public EmployeeDto findById(Long id) {
        return employeeRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Employee not found: " + id));
    }

    public List<EmployeeDto> findByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::toDto)
                .toList();
    }

    private EmployeeDto toDto(Employee emp) {
        return new EmployeeDto(
                emp.getId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmail(),
                emp.getHireDate(),
                emp.getDepartment() != null ? emp.getDepartment().getId() : null,
                emp.getDepartment() != null ? emp.getDepartment().getName() : null
        );
    }
}
