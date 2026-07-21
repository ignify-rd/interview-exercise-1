package com.ignify.taskflow.controller;

import com.ignify.taskflow.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_returnsSeededEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(50)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].email").exists());
    }

    @Test
    void getAll_filteredByDepartment_returnsOnlyThatDepartment() throws Exception {
        mockMvc.perform(get("/api/employees").param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[*].departmentId", everyItem(is(1))));
    }

    @Test
    void getById_returnsEmployee() throws Exception {
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.departmentName").exists());
    }

    @Test
    void getById_invalidIdType_returns400() throws Exception {
        mockMvc.perform(get("/api/employees/not-a-number"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_unknownId_raisesNotFound() {
        assertThatThrownBy(() -> mockMvc.perform(get("/api/employees/999999")))
                .hasRootCauseInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getAll_hasPositiveCount() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }
}
