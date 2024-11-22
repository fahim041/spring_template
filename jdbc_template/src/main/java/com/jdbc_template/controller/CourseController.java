package com.jdbc_template.controller;

import com.jdbc_template.entity.Course;
import com.jdbc_template.exception.NotFoundException;
import com.jdbc_template.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public List<Course> getCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourse(@PathVariable("id") int id){
        return courseService.getCourse(id);
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable("id") int id, @RequestBody Course course){
        Course c = courseService.getCourse(id);

        if(c == null){
            throw new NotFoundException("Course not found with id "+ id);
        }
        course.setId(id);
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") int id){
        boolean deleted = courseService.deleteStudent(id);

        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Deletion failed");
        }
    }
}
