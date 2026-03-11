package com.ignify.taskflow.dto;

import java.time.LocalDate;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate hireDate,
        Long departmentId,
        String departmentName
) {
}
