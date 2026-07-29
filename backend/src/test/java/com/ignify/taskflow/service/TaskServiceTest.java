package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.PagedResponse;
import com.ignify.taskflow.dto.TaskDto;
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
    @DisplayName("findAll without employeeId delegates to findAll and maps the page metadata")
    void findAllWithoutEmployeeFilter() {
        Task task = task(1L, "Write tests");
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());
        when(taskRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task), pageable, 1));

        PagedResponse<TaskDto> response = taskService.findAll(0, 20, null);

        verify(taskRepository).findAll(any(Pageable.class));
        verify(taskRepository, never()).findByAssignedToId(any(), any(Pageable.class));

        assertThat(response.page()).isZero();
        assertThat(response.size()).isEqualTo(20);
        assertThat(response.totalElements()).isEqualTo(1);
        assertThat(response.totalPages()).isEqualTo(1);
        assertThat(response.last()).isTrue();
        assertThat(response.content())
                .singleElement()
                .satisfies(dto -> {
                    assertThat(dto.id()).isEqualTo(1L);
                    assertThat(dto.title()).isEqualTo("Write tests");
                });
    }

    @Test
    @DisplayName("findAll with employeeId delegates to findByAssignedToId sorted by createdAt desc")
    void findAllWithEmployeeFilter() {
        Task task = task(2L, "Review PR");
        Pageable pageable = PageRequest.of(1, 5, Sort.by("createdAt").descending());
        when(taskRepository.findByAssignedToId(eq(7L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(task), pageable, 12));

        PagedResponse<TaskDto> response = taskService.findAll(1, 5, 7L);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(taskRepository).findByAssignedToId(eq(7L), pageableCaptor.capture());
        verify(taskRepository, never()).findAll(any(Pageable.class));

        Pageable captured = pageableCaptor.getValue();
        assertThat(captured.getPageNumber()).isEqualTo(1);
        assertThat(captured.getPageSize()).isEqualTo(5);
        assertThat(captured.getSort().getOrderFor("createdAt")).isNotNull();
        assertThat(captured.getSort().getOrderFor("createdAt").getDirection())
                .isEqualTo(Sort.Direction.DESC);

        assertThat(response.page()).isEqualTo(1);
        assertThat(response.totalElements()).isEqualTo(12);
        assertThat(response.last()).isFalse();
        assertThat(response.content()).extracting(TaskDto::title).containsExactly("Review PR");
    }

    @Test
    @DisplayName("findById maps every field including project and assignee")
    void findByIdMapsFullTask() {
        Task task = task(3L, "Ship feature");
        task.setDescription("Ship the workload dashboard");
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setPriority(Priority.HIGH);
        task.setEstimatedHours(8);
        task.setActualHours(3);
        task.setDueDate(LocalDate.of(2026, 3, 1));
        task.setCreatedAt(LocalDateTime.of(2026, 2, 1, 9, 30));
        task.setProject(project(10L, "TaskFlow"));
        task.setAssignedTo(employee(20L, "Anh", "Nguyen"));
        when(taskRepository.findById(3L)).thenReturn(Optional.of(task));

        TaskDto dto = taskService.findById(3L);

        assertThat(dto.id()).isEqualTo(3L);
        assertThat(dto.title()).isEqualTo("Ship feature");
        assertThat(dto.description()).isEqualTo("Ship the workload dashboard");
        assertThat(dto.status()).isEqualTo("IN_PROGRESS");
        assertThat(dto.priority()).isEqualTo("HIGH");
        assertThat(dto.estimatedHours()).isEqualTo(8);
        assertThat(dto.actualHours()).isEqualTo(3);
        assertThat(dto.dueDate()).isEqualTo(LocalDate.of(2026, 3, 1));
        assertThat(dto.createdAt()).isEqualTo(LocalDateTime.of(2026, 2, 1, 9, 30));
        assertThat(dto.projectId()).isEqualTo(10L);
        assertThat(dto.projectName()).isEqualTo("TaskFlow");
        assertThat(dto.assignedToId()).isEqualTo(20L);
        assertThat(dto.assignedToName()).isEqualTo("Anh Nguyen");
    }

    @Test
    @DisplayName("findById leaves project and assignee fields null when the relations are missing")
    void findByIdMapsTaskWithoutRelations() {
        Task task = task(4L, "Unassigned chore");
        when(taskRepository.findById(4L)).thenReturn(Optional.of(task));

        TaskDto dto = taskService.findById(4L);

        assertThat(dto.projectId()).isNull();
        assertThat(dto.projectName()).isNull();
        assertThat(dto.assignedToId()).isNull();
        assertThat(dto.assignedToName()).isNull();
    }

    @Test
    @DisplayName("findById throws NoSuchElementException when the task does not exist")
    void findByIdThrowsWhenMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.findById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Task not found: 99");
    }

    private static Task task(Long id, String title) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setStatus(TaskStatus.TODO);
        task.setPriority(Priority.MEDIUM);
        task.setCreatedAt(LocalDateTime.of(2026, 1, 15, 8, 0));
        return task;
    }

    private static Project project(Long id, String name) {
        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setStatus("ACTIVE");
        return project;
    }

    private static Employee employee(Long id, String firstName, String lastName) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com");
        return employee;
    }
}
