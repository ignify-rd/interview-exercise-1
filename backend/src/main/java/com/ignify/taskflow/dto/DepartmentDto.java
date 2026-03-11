package com.ignify.taskflow.dto;

public record DepartmentDto(
        Long id,
        String name,
        String description,
        int employeeCount
) {
}
