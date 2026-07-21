package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.PagedResponse;
import com.ignify.taskflow.dto.TaskDto;
import com.ignify.taskflow.model.Employee;
import com.ignify.taskflow.model.Priority;
import com.ignify.taskflow.model.Project;
import com.ignify.taskflow.model.Task;
import com.ignify.taskflow.model.TaskStatus;
import com.ignify.taskflow.repository.TaskRepository;
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

    private Task task(Long id) {
        Project project = new Project();
        project.setId(2L);
        project.setName("Mobile App v2");

        Employee employee = new Employee();
        employee.setId(3L);
        employee.setFirstName("Carol");
        employee.setLastName("Williams");

        Task t = new Task();
        t.setId(id);
        t.setTitle("Task #" + id);
        t.setDescription("Implement and verify the deliverable");
        t.setStatus(TaskStatus.IN_PROGRESS);
        t.setPriority(Priority.HIGH);
        t.setEstimatedHours(4);
        t.setActualHours(2);
        t.setDueDate(LocalDate.of(2024, 6, 1));
        t.setCreatedAt(LocalDateTime.of(2024, 1, 1, 10, 0));
        t.setProject(project);
        t.setAssignedTo(employee);
        return t;
    }

    @Test
    void findAll_withoutEmployeeId_usesFindAllAndMaps() {
        Page<Task> page = new PageImpl<>(List.of(task(1L)), PageRequest.of(0, 20), 1);
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(page);

        PagedResponse<TaskDto> response = taskService.findAll(0, 20, null);

        assertThat(response.content()).hasSize(1);
        assertThat(response.totalElements()).isEqualTo(1);
        assertThat(response.page()).isEqualTo(0);
        assertThat(response.size()).isEqualTo(20);
        assertThat(response.last()).isTrue();

        TaskDto dto = response.content().get(0);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.title()).isEqualTo("Task #1");
        assertThat(dto.status()).isEqualTo("IN_PROGRESS");
        assertThat(dto.priority()).isEqualTo("HIGH");
        assertThat(dto.projectId()).isEqualTo(2L);
        assertThat(dto.projectName()).isEqualTo("Mobile App v2");
        assertThat(dto.assignedToId()).isEqualTo(3L);
        assertThat(dto.assignedToName()).isEqualTo("Carol Williams");

        verify(taskRepository, never()).findByAssignedToId(any(), any(Pageable.class));
    }

    @Test
    void findAll_sortsByCreatedAtDescending() {
        Page<Task> page = new PageImpl<>(List.of(task(1L)));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(page);

        taskService.findAll(1, 5, null);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskRepository).findAll(captor.capture());
        Pageable pageable = captor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(1);
        assertThat(pageable.getPageSize()).isEqualTo(5);
        assertThat(pageable.getSort().getOrderFor("createdAt")).isNotNull();
        assertThat(pageable.getSort().getOrderFor("createdAt").isDescending()).isTrue();
    }

    @Test
    void findAll_withEmployeeId_filtersByAssignee() {
        Page<Task> page = new PageImpl<>(List.of(task(1L)));
        when(taskRepository.findByAssignedToId(eq(3L), any(Pageable.class))).thenReturn(page);

        PagedResponse<TaskDto> response = taskService.findAll(0, 20, 3L);

        assertThat(response.content()).hasSize(1);
        verify(taskRepository).findByAssignedToId(eq(3L), any(Pageable.class));
        verify(taskRepository, never()).findAll(any(Pageable.class));
    }

    @Test
    void findById_returnsDto_whenPresent() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task(1L)));

        TaskDto dto = taskService.findById(1L);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.title()).isEqualTo("Task #1");
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Task not found: 999");
    }
}
