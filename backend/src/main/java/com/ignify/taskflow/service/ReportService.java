package com.ignify.taskflow.service;

import com.ignify.taskflow.dto.EmployeeWorkloadDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ReportService {

    /**
     * Returns the employee workload summary.
     */
    @Transactional(readOnly = true)
    public List<EmployeeWorkloadDto> getEmployeeWorkload() {
        return Collections.emptyList();
    }
}
