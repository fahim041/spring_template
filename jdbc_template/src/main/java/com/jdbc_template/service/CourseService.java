package com.jdbc_template.service;

import com.jdbc_template.entity.Course;
import com.jdbc_template.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.getAllCourses();
    }

    public Course addCourse(Course c){
        return courseRepository.save(c);
    }

    public Course getCourse(int id){
        return courseRepository.getById(id);
    }

    public Course updateCourse(Course c){
        return courseRepository.update(c);
    }

    public boolean deleteStudent(int id){
        return courseRepository.delete(id);
    }

    public boolean enrollCourse(int courseId, int studentId){
        boolean alreadyEnrolled = courseRepository.checkAlreadyEnrolled(courseId, studentId);

        if(alreadyEnrolled)
            throw new RuntimeException("Student is already enrolled in this course");

        return courseRepository.enroll(courseId, studentId);
    }
}
