package com.ignify.taskflow.dto;

public record EmployeeWorkloadDto(
        Long id,
        String fullName,
        String email,
        String department,
        long totalTasks,
        long activeTasks,
        long overdueTasks,
        double estimatedHours,
        double completionRate
) {
}
