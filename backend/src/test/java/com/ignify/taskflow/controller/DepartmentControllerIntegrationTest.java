package com.ignify.taskflow.controller;

import com.ignify.taskflow.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DepartmentControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_returnsSeededDepartments() throws Exception {
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].employeeCount", greaterThanOrEqualTo(0)));
    }

    @Test
    void getById_returnsDepartmentWithEmployeeCount() throws Exception {
        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.employeeCount", is(10)));
    }

    @Test
    void getById_invalidIdType_returns400() throws Exception {
        mockMvc.perform(get("/api/departments/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_unknownId_raisesNotFound() {
        assertThatThrownBy(() -> mockMvc.perform(get("/api/departments/999999")))
                .hasRootCauseInstanceOf(NoSuchElementException.class);
    }
}
