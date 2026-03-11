package com.ignify.taskflow.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskDto(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        Integer estimatedHours,
        Integer actualHours,
        LocalDate dueDate,
        LocalDateTime createdAt,
        Long projectId,
        String projectName,
        Long assignedToId,
        String assignedToName
) {
}
