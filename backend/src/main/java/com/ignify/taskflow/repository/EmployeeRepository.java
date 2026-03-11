package com.ignify.taskflow.repository;

import com.ignify.taskflow.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();

    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.department.id = :departmentId")
    List<Employee> findByDepartmentId(Long departmentId);

    long countByDepartmentId(Long departmentId);
}
