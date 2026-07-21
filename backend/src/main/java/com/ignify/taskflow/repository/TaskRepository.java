package com.ignify.taskflow.repository;

import com.ignify.taskflow.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long employeeId);

    Page<Task> findByAssignedToId(Long employeeId, Pageable pageable);

    Page<Task> findByProjectId(Long projectId, Pageable pageable);
}
