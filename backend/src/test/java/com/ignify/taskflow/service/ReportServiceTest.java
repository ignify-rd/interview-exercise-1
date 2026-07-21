package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.EmployeeWorkloadDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportServiceTest {

    private final ReportService reportService = new ReportService();

    @Test
    void getEmployeeWorkload_returnsEmptyList() {
        List<EmployeeWorkloadDto> result = reportService.getEmployeeWorkload();

        assertThat(result).isNotNull().isEmpty();
    }
}
