package com.spring_template.entity_manager.service;

import com.spring_template.entity_manager.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Integer id);
    Employee save(Employee employee);
    void deleteEmployee(Integer id);
}
