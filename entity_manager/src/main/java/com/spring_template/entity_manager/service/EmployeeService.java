package com.spring_template.entity_manager.service;

import com.spring_template.entity_manager.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteEmployee(int id);
}
