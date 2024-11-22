package com.jdbc_template.service;

import com.jdbc_template.entity.Student;
import com.jdbc_template.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.getAllStudents();
    }

    public Student addStudent(Student s){
        return studentRepository.save(s);
    }

    public Student getStudent(int id){
        return studentRepository.getById(id);
    }

    public Student updateStudent(Student s){
        return studentRepository.update(s);
    }

    public boolean deleteStudent(int id){
        return studentRepository.delete(id);
    }
}
