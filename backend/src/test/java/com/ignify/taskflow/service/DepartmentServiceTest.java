package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.DepartmentDto;
import com.ignify.taskflow.model.Department;
import com.ignify.taskflow.repository.DepartmentRepository;
import com.ignify.taskflow.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department(Long id) {
        Department d = new Department();
        d.setId(id);
        d.setName("Engineering");
        d.setDescription("Software engineering and platform team");
        return d;
    }

    @Test
    void findAll_mapsWithEmployeeCount() {
        when(departmentRepository.findAll()).thenReturn(List.of(department(1L)));
        when(employeeRepository.countByDepartmentId(1L)).thenReturn(10L);

        List<DepartmentDto> result = departmentService.findAll();

        assertThat(result).hasSize(1);
        DepartmentDto dto = result.get(0);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Engineering");
        assertThat(dto.description()).isEqualTo("Software engineering and platform team");
        assertThat(dto.employeeCount()).isEqualTo(10);
    }

    @Test
    void findById_returnsDtoWithCount_whenPresent() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department(1L)));
        when(employeeRepository.countByDepartmentId(1L)).thenReturn(7L);

        DepartmentDto dto = departmentService.findById(1L);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.employeeCount()).isEqualTo(7);
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.findById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Department not found: 999");
    }
}
