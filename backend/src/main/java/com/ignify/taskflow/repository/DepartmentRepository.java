package com.ignify.taskflow.repository;

import com.ignify.taskflow.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
