package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.EmployeeDto;
import com.ignify.taskflow.model.Department;
import com.ignify.taskflow.model.Employee;
import com.ignify.taskflow.repository.EmployeeRepository;
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
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee(Long id, Department dept) {
        Employee e = new Employee();
        e.setId(id);
        e.setFirstName("Alice");
        e.setLastName("Johnson");
        e.setEmail("alice.johnson@taskflow.io");
        e.setHireDate(LocalDate.of(2021, 3, 15));
        e.setDepartment(dept);
        return e;
    }

    private Department department(Long id) {
        Department d = new Department();
        d.setId(id);
        d.setName("Engineering");
        return d;
    }

    @Test
    void findAll_mapsEntitiesToDtos() {
        Department dept = department(1L);
        when(employeeRepository.findAllWithDepartment()).thenReturn(List.of(employee(1L, dept)));

        List<EmployeeDto> result = employeeService.findAll();

        assertThat(result).hasSize(1);
        EmployeeDto dto = result.get(0);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.firstName()).isEqualTo("Alice");
        assertThat(dto.lastName()).isEqualTo("Johnson");
        assertThat(dto.email()).isEqualTo("alice.johnson@taskflow.io");
        assertThat(dto.departmentId()).isEqualTo(1L);
        assertThat(dto.departmentName()).isEqualTo("Engineering");
    }

    @Test
    void findById_returnsDto_whenPresent() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee(1L, department(1L))));

        EmployeeDto dto = employeeService.findById(1L);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.email()).isEqualTo("alice.johnson@taskflow.io");
    }

    @Test
    void findById_nullDepartment_yieldsNullDepartmentFields() {
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee(2L, null)));

        EmployeeDto dto = employeeService.findById(2L);

        assertThat(dto.departmentId()).isNull();
        assertThat(dto.departmentName()).isNull();
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Employee not found: 999");
    }

    @Test
    void findByDepartment_mapsEntitiesToDtos() {
        Department dept = department(1L);
        when(employeeRepository.findByDepartmentId(1L)).thenReturn(List.of(employee(1L, dept)));

        List<EmployeeDto> result = employeeService.findByDepartment(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).departmentId()).isEqualTo(1L);
    }
}
