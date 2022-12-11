package com.yondu.university.project_rohan.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GradeSheetDto {
    @JsonProperty(value = "course_code")
    private String courseCode;

    @JsonProperty(value = "batch_number")
    private Integer batch;

    @JsonInclude(Include.NON_EMPTY)
    private List<UserDto> students;

    /**
     * 
     */
    public GradeSheetDto(CourseClassDto courseClassDto) {
        courseClassDto.withStudents(true).withCourseCode();
        this.courseCode = courseClassDto.getCourseCode();
        this.batch = courseClassDto.getBatch();
        this.students = courseClassDto.getStudents();
    }

    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return the batch
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * @return the students
     */
    public List<UserDto> getStudents() {
        return students;
    }

}
