package com.jdbc_template.repository;

import com.jdbc_template.entity.Course;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
        String sql = "SELECT * FROM course WHERE id = ?";

        List<Course> courses = jdbcTemplate.query(sql, new Course.CourseMapper(), id);

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
