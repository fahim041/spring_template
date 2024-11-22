package com.jdbc_template.repository;

import com.jdbc_template.entity.Course;
import com.jdbc_template.entity.Enrollment;
import com.jdbc_template.entity.Student;
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
public class CourseRepository {
    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Course> getAllCourses(){
        String sql = "SELECT * from course";

        return jdbcTemplate.query(sql, new Course.CourseMapper());
    }

    public Course save(Course c){
        String sql = "INSERT INTO course (name, description) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, c.getName());
                ps.setString(2, c.getDescription());
                return ps;
            }, keyHolder);
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException("Bad request");
        }

        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return getById(generatedId);
    }

    public Course getById(int id){
//        String sql = "SELECT * FROM course WHERE id = ?";
        String sql = "SELECT c.id AS course_id, c.name AS course_name, c.description AS course_description, " +
                "c.created_date AS course_created_date, c.updated_date AS course_updated_date, " +
                "s.id AS student_id, s.name AS student_name, s.email AS student_email, " +
                "e.created_date AS enrollment_date " +
                "FROM course c " +
                "LEFT JOIN enrollment e ON c.id = e.course_id " +
                "LEFT JOIN student s ON e.student_id = s.id " +
                "WHERE c.id = ?";

        List<Course> courses = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            Course course = new Course();
            List<Enrollment> students = new ArrayList<>();

            course.setId(rs.getInt("course_id"));
            course.setName(rs.getString("course_name"));
            course.setDescription(rs.getString("course_description"));
            if(rs.getTimestamp("course_created_date") != null)
                course.setCreatedDate(new Timestamp(rs.getTimestamp("course_created_date").getTime()));
            if(rs.getTimestamp("course_updated_date") != null)
                course.setUpdatedDate(new Timestamp(rs.getTimestamp("course_updated_date").getTime()));

            do {
                // For each student, create a student object
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setName(rs.getString("student_name"));
                student.setEmail(rs.getString("student_email"));

                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setEnrollmentDate(rs.getTimestamp("enrollment_date"));

                if(rs.getInt("student_id") != 0)
                    students.add(enrollment);
            } while(rs.next());

            if(!students.isEmpty()){
                course.setStudents(students);
            }

            return course;
        });

        if(courses.isEmpty()){
            return null;
        }

        return courses.get(0);
    }

    public Course update(Course c){
        String sql = "UPDATE course SET name = ?, description = ?, updated_date = ? WHERE id = ?";

        Timestamp current = new Timestamp(System.currentTimeMillis());

        int rowsAffected = jdbcTemplate.update(sql, c.getName(), c.getDescription(), current, c.getId());

        if(rowsAffected == 0){
            throw new RuntimeException("Update failed");
        }

        return getById(c.getId());
    }

    public boolean delete(int id){
        String sql = "DELETE FROM course WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);

        return rowsAffected > 0;
    }
}
