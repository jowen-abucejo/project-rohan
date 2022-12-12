package com.yondu.university.project_rohan.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class GradeSheetDto {
    @JsonProperty(value = "course_code", index = 1)
    private String courseCode;

    @JsonProperty(value = "batch_number", index = 2)
    private Integer batch;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 3)
    private List<UserDto> students;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 4)
    private Map<String, List<ScoreDto>> scores = new HashMap<>();

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
     * 
     */
    public GradeSheetDto(UserDto studentDto, String courseCode, Integer batch) {
        studentDto.withScoresByClass(courseCode, batch);
        this.courseCode = courseCode;
        this.batch = batch;
        this.scores = studentDto.getScores();
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

    /**
     * @return the scores
     */
    public Map<String, List<ScoreDto>> getScores() {
        return scores;
    }
}
