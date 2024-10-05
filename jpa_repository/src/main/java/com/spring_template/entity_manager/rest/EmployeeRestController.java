package com.spring_template.entity_manager.rest;

import com.spring_template.entity_manager.entity.Employee;
import com.spring_template.entity_manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee findEmployee(@PathVariable Integer id){
        Optional<Employee> employee = employeeService.findById(id);

        return employee.orElseThrow(() -> new RuntimeException("Employee id not found - " + id));
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
