package com.spring_template.entity_manager.dao;

import com.spring_template.entity_manager.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
