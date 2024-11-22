package com.jdbc_template.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private Date enrollmentDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Course course;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Student student;
}
