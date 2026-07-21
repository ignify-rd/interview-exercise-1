package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.ProjectDto;
import com.ignify.taskflow.model.Project;
import com.ignify.taskflow.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project(Long id) {
        Project p = new Project();
        p.setId(id);
        p.setName("Platform Revamp");
        p.setDescription("Re-architect the core platform for scalability");
        p.setStartDate(LocalDate.of(2024, 1, 1));
        p.setEndDate(LocalDate.of(2024, 12, 31));
        p.setStatus("ACTIVE");
        return p;
    }

    @Test
    void findAll_mapsEntitiesToDtos() {
        when(projectRepository.findAll()).thenReturn(List.of(project(1L)));

        List<ProjectDto> result = projectService.findAll();

        assertThat(result).hasSize(1);
        ProjectDto dto = result.get(0);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Platform Revamp");
        assertThat(dto.status()).isEqualTo("ACTIVE");
        assertThat(dto.startDate()).isEqualTo(LocalDate.of(2024, 1, 1));
    }

    @Test
    void findById_returnsDto_whenPresent() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project(1L)));

        ProjectDto dto = projectService.findById(1L);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Platform Revamp");
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.findById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Project not found: 999");
    }
}
