package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.PagedResponse;
import com.ignify.taskflow.dto.TaskDto;
import com.ignify.taskflow.model.Department;
import com.ignify.taskflow.model.Employee;
import com.ignify.taskflow.model.Priority;
import com.ignify.taskflow.model.Project;
import com.ignify.taskflow.model.Task;
import com.ignify.taskflow.model.TaskStatus;
import com.ignify.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("findAll without employeeId reads every task, newest first")
    void findAllReturnsAllTasksSortedByCreatedAtDescending() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task()), pageable, 1));

        PagedResponse<TaskDto> response = taskService.findAll(0, 20, null);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskRepository).findAll(captor.capture());
        assertThat(captor.getValue().getPageNumber()).isZero();
        assertThat(captor.getValue().getPageSize()).isEqualTo(20);
        assertThat(captor.getValue().getSort()).isEqualTo(Sort.by("createdAt").descending());

        verify(taskRepository, never()).findByAssignedToId(any(), any(Pageable.class));

        assertThat(response.page()).isZero();
        assertThat(response.size()).isEqualTo(20);
        assertThat(response.totalElements()).isEqualTo(1);
        assertThat(response.totalPages()).isEqualTo(1);
        assertThat(response.last()).isTrue();
        assertThat(response.content()).hasSize(1);
    }

    @Test
    @DisplayName("findAll with employeeId only reads that employee's tasks")
    void findAllFiltersByAssignedEmployeeWhenEmployeeIdGiven() {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("createdAt").descending());
        when(taskRepository.findByAssignedToId(eq(7L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task()), pageable, 12));

        PagedResponse<TaskDto> response = taskService.findAll(1, 5, 7L);

        verify(taskRepository, never()).findAll(any(Pageable.class));
        assertThat(response.page()).isEqualTo(1);
        assertThat(response.size()).isEqualTo(5);
        assertThat(response.totalElements()).isEqualTo(12);
        assertThat(response.totalPages()).isEqualTo(3);
        assertThat(response.last()).isFalse();
    }

    @Test
    @DisplayName("a task is mapped to its DTO with project and assignee flattened")
    void findAllMapsTaskEntityOntoDto() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Task task = task();
        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task), pageable, 1));

        TaskDto dto = taskService.findAll(0, 20, null).content().get(0);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.title()).isEqualTo("Ship the workload dashboard");
        assertThat(dto.description()).isEqualTo("End-to-end feature");
        assertThat(dto.status()).isEqualTo("IN_PROGRESS");
        assertThat(dto.priority()).isEqualTo("HIGH");
        assertThat(dto.estimatedHours()).isEqualTo(8);
        assertThat(dto.actualHours()).isEqualTo(3);
        assertThat(dto.dueDate()).isEqualTo(LocalDate.of(2025, 1, 31));
        assertThat(dto.createdAt()).isEqualTo(LocalDateTime.of(2025, 1, 2, 9, 30));
        assertThat(dto.projectId()).isEqualTo(10L);
        assertThat(dto.projectName()).isEqualTo("TaskFlow");
        assertThat(dto.assignedToId()).isEqualTo(7L);
        assertThat(dto.assignedToName()).isEqualTo("Ada Lovelace");
    }

    @Test
    @DisplayName("an unassigned task with no project maps to null instead of blowing up")
    void findAllMapsTaskWithoutProjectOrAssignee() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Task orphan = task();
        orphan.setProject(null);
        orphan.setAssignedTo(null);
        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(orphan), pageable, 1));

        TaskDto dto = taskService.findAll(0, 20, null).content().get(0);

        assertThat(dto.projectId()).isNull();
        assertThat(dto.projectName()).isNull();
        assertThat(dto.assignedToId()).isNull();
        assertThat(dto.assignedToName()).isNull();
    }

    @Test
    @DisplayName("findById returns the mapped task when it exists")
    void findByIdReturnsMappedTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task()));

        TaskDto dto = taskService.findById(1L);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.title()).isEqualTo("Ship the workload dashboard");
    }

    @Test
    @DisplayName("findById throws with the missing id in the message")
    void findByIdThrowsWhenTaskIsMissing() {
        when(taskRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(404L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("404");
    }

    @Test
    @DisplayName("an empty page yields an empty, last page response")
    void findAllHandlesEmptyPage() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        Page<Task> empty = new PageImpl<>(List.of(), pageable, 0);
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(empty);

        PagedResponse<TaskDto> response = taskService.findAll(0, 20, null);

        assertThat(response.content()).isEmpty();
        assertThat(response.totalElements()).isZero();
        assertThat(response.last()).isTrue();
    }

    private Task task() {
        Department department = new Department();
        department.setId(3L);
        department.setName("Engineering");

        Employee employee = new Employee();
        employee.setId(7L);
        employee.setFirstName("Ada");
        employee.setLastName("Lovelace");
        employee.setEmail("ada@ignify.io");
        employee.setDepartment(department);

        Project project = new Project();
        project.setId(10L);
        project.setName("TaskFlow");
        project.setStatus("ACTIVE");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Ship the workload dashboard");
        task.setDescription("End-to-end feature");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(Priority.HIGH);
        task.setEstimatedHours(8);
        task.setActualHours(3);
        task.setDueDate(LocalDate.of(2025, 1, 31));
        task.setCreatedAt(LocalDateTime.of(2025, 1, 2, 9, 30));
        task.setProject(project);
        task.setAssignedTo(employee);
        return task;
    }
}
