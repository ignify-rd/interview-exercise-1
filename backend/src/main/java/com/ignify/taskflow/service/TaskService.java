package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.PagedResponse;
import com.ignify.taskflow.dto.TaskDto;
import com.ignify.taskflow.model.Task;
import com.ignify.taskflow.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public PagedResponse<TaskDto> findAll(int page, int size, Long employeeId) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Task> result = (employeeId != null)
                ? taskRepository.findByAssignedToId(employeeId, pageable)
                : taskRepository.findAll(pageable);
        return PagedResponse.from(result.map(this::toDto));
    }

    public TaskDto findById(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    }

    private TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getEstimatedHours(),
                task.getActualHours(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getProject() != null ? task.getProject().getId() : null,
                task.getProject() != null ? task.getProject().getName() : null,
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null,
                task.getAssignedTo() != null
                        ? task.getAssignedTo().getFirstName() + " " + task.getAssignedTo().getLastName()
                        : null
        );
    }
}
