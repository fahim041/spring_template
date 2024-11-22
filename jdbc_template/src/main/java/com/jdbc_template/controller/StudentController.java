package com.jdbc_template.controller;

import com.jdbc_template.entity.Student;
import com.jdbc_template.exception.NotFoundException;
import com.jdbc_template.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable("id") int id){
        Student student = studentService.getStudent(id);

        if(student == null){
            throw new NotFoundException("Student not found with id: " + id);
        }

        return student;
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable("id") int id, @RequestBody Student student){
        Student s = studentService.getStudent(id);

        if(s == null){
            throw new NotFoundException("Student not found with id "+ id);
        }

        return studentService.updateStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id){
        boolean deleted = studentService.deleteStudent(id);

        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            throw  new RuntimeException("Deletion failed");
        }
    }
}
