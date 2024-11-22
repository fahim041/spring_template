package com.jdbc_template.repository;

import com.jdbc_template.entity.Student;
import com.jdbc_template.exception.DuplicateEmailException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
        String sql = "SELECT * FROM student where id = ?";

        List<Student> students = jdbcTemplate.query(sql, new Student.StudentMapper(), id);

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
