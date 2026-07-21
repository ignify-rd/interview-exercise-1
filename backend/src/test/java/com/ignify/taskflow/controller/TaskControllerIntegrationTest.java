package com.ignify.taskflow.controller;

import com.ignify.taskflow.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_returnsPagedTasks() throws Exception {
        mockMvc.perform(get("/api/tasks").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(5)))
                .andExpect(jsonPath("$.page", is(0)))
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.totalElements", greaterThan(0)))
                .andExpect(jsonPath("$.totalPages", greaterThan(1)))
                .andExpect(jsonPath("$.last", is(false)));
    }

    @Test
    void getAll_usesDefaultPagingWhenNoParams() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(20)))
                .andExpect(jsonPath("$.content.length()", is(20)));
    }

    @Test
    void getAll_filteredByEmployee_returnsOnlyThatAssignee() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "10")
                        .param("employeeId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].assignedToId", org.hamcrest.Matchers.everyItem(is(1))));
    }

    @Test
    void getById_returnsTask() throws Exception {
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task #1")))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.priority").exists());
    }

    @Test
    void getById_invalidIdType_returns400() throws Exception {
        mockMvc.perform(get("/api/tasks/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_unknownId_raisesNotFound() {
        assertThatThrownBy(() -> mockMvc.perform(get("/api/tasks/9999999")))
                .hasRootCauseInstanceOf(NoSuchElementException.class);
    }
}
