package com.jdbc_template.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String name;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Student> students;

    public static class CourseMapper implements RowMapper<Course>{

        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setDescription(rs.getString("description"));
            course.setCreatedDate(new Date(rs.getTimestamp("created_date").getTime()));

            if(rs.getTimestamp("updated_date") != null)
                course.setUpdatedDate(new Date(rs.getTimestamp("updated_date").getTime()));

            return course;
        }
    }
}
