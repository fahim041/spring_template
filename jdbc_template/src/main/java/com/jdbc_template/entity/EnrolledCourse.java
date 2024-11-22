package com.jdbc_template.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledCourse {
    private Course course;
    private Date enrollmentDate;
}
