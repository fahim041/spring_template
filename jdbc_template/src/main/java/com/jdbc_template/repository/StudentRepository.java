package com.jdbc_template.repository;

import com.jdbc_template.entity.Course;
import com.jdbc_template.entity.EnrolledCourse;
import com.jdbc_template.entity.Student;
import com.jdbc_template.exception.DuplicateEmailException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> getAllStudents(){
        String sql = "SELECT * FROM student";

        return jdbcTemplate.query(sql, new Student.StudentMapper());
    }

    public Student save(Student s){
        String sql = "INSERT INTO student (name, email) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, s.getName());
                ps.setString(2, s.getEmail());
                return ps;
            }, keyHolder);
        } catch (DataIntegrityViolationException e){
            throw new DuplicateEmailException(s.getEmail() + " already taken!");
        }

        int generateId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return getById(generateId);
    }

    public Student getById(int id){
        
        String sql = "SELECT s.id AS student_id, s.name AS student_name, s.email AS student_email, " +
                "c.id AS course_id, c.name AS course_name, c.description AS course_description, " +
                "e.created_date AS enrollment_date " +
                "FROM student s " +
                "LEFT JOIN enrollment e ON s.id = e.student_id " +
                "LEFT JOIN course c ON e.course_id = c.id " +
                "WHERE s.id = ?";

        List<Student> students = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Student student = null;
            List<EnrolledCourse> enrolledCourses = new ArrayList<>();

            student = new Student();
            student.setId(rs.getInt("student_id"));
            student.setName(rs.getString("student_name"));
            student.setEmail(rs.getString("student_email"));

            do {
                // For each row, create a Course object
                Course course = new Course();
                course.setId(rs.getInt("course_id"));
                course.setName(rs.getString("course_name"));
                course.setDescription(rs.getString("course_description"));

                // Create an Enrollment object to hold the course and enrollment date
                EnrolledCourse enrollment = new EnrolledCourse();
                enrollment.setCourse(course);
                enrollment.setEnrollmentDate(rs.getTimestamp("enrollment_date"));

                // Add to the list of enrolled courses
                if(rs.getInt("course_id") > 0) {
                    enrolledCourses.add(enrollment);
                }
            } while (rs.next()); // Continue to process the next rows, if any

            // Assign the list of enrolled courses to the student
            if(!enrolledCourses.isEmpty())
                student.setEnrolledCourses(enrolledCourses);


            return student;
        });

        if(students.isEmpty()){
            return null;
        }
        return students.get(0);
    }

    public Student update(Student s){
        String sql = "UPDATE student SET name = ?, email = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, s.getName(), s.getEmail(), s.getId());

        if(rowsAffected == 0){
            throw new RuntimeException("Update failed");
        }

        return getById(s.getId());
    }

    public boolean delete(int id){
        String sql = "DELETE FROM student WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);

        return rowsAffected > 0;
    }

}
