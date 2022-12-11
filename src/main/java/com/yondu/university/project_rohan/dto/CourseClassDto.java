package com.yondu.university.project_rohan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yondu.university.project_rohan.entity.CourseClass;
import com.yondu.university.project_rohan.validation.CourseCodeExists;
import com.yondu.university.project_rohan.validation.DayCountMin;
import com.yondu.university.project_rohan.validation.HundredGradingPercentage;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@HundredGradingPercentage(fieldNames = { "quizPercentage", "exercisePercentage", "projectPercentage",
        "attendancePercentage" })
@DayCountMin(start = "startDate", end = "endDate", min = 1)
public class CourseClassDto {
    @NotBlank(message = "Course code is required")
    @CourseCodeExists
    @JsonInclude(value = Include.NON_EMPTY)
    @JsonProperty(value = "course_code")
    private String courseCode;

    @JsonInclude(value = Include.NON_EMPTY)
    private CourseDto course;

    private Integer batch;

    @NotNull(message = "Quiz percentage is required")
    @DecimalMax(value = "100.00", message = "Quiz percentage maximum value is 100.00")
    @DecimalMin(value = "0.00", message = "Quiz percentage minimum value is 0.00")
    @Digits(integer = 3, fraction = 2, message = "Quiz percentage value out of bounds. <3 digits>.<2 digits>")
    @JsonProperty(value = "quiz_percentage")
    private BigDecimal quizPercentage;

    @NotNull(message = "Exercise percentage is required")
    @DecimalMax(value = "100.00", message = "Exercise percentage maximum value is 100.00")
    @DecimalMin(value = "0.00", message = "Exercise percentage minimum value is 0.00")
    @Digits(integer = 3, fraction = 2, message = "Exercise percentage value out of bounds. <3 digits>.<2 digits>")
    @JsonProperty(value = "exercise_percentage")
    private BigDecimal exercisePercentage;

    @NotNull(message = "Project percentage is required")
    @DecimalMax(value = "100.00", message = "Project percentage maximum value is 100.00")
    @DecimalMin(value = "0.00", message = "Project percentage minimum value is 0.00")
    @Digits(integer = 3, fraction = 2, message = "Project percentage value out of bounds. <3 digits>.<2 digits>")
    @JsonProperty(value = "project_percentage")
    private BigDecimal projectPercentage;

    @NotNull(message = "Attendance percentage is required")
    @DecimalMax(value = "100.00", message = "Attendance percentage maximum value is 100.00")
    @DecimalMin(value = "0.00", message = "Attendance percentage minimum value is 0.00")
    @Digits(integer = 3, fraction = 2, message = "Attendance percentage value out of bounds. <3 digits>.<2 digits>")
    @JsonProperty(value = "attendance_percentage")
    private BigDecimal attendancePercentage;

    @NotNull(message = "Start date is required")
    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonProperty(value = "end_date")
    private LocalDate endDate;

    @JsonInclude(value = Include.NON_EMPTY)
    private String status;

    @JsonInclude(value = Include.NON_EMPTY)
    private UserDto sme;

    @JsonInclude(value = Include.NON_EMPTY)
    private List<UserDto> students;

    @JsonIgnore
    private CourseClass courseClass;

    /**
     * 
     */
    public CourseClassDto() {
    }

    /**
     * @param courseCode
     * @param quizPercentage
     * @param exercisePercentage
     * @param projectPercentage
     * @param attendancePercentage
     * @param startDate
     * @param endDate
     */
    public CourseClassDto(String courseCode, BigDecimal quizPercentage,
            BigDecimal exercisePercentage, BigDecimal projectPercentage, BigDecimal attendancePercentage,
            LocalDate startDate, LocalDate endDate) {
        this.courseCode = courseCode;
        this.quizPercentage = quizPercentage;
        this.exercisePercentage = exercisePercentage;
        this.projectPercentage = projectPercentage;
        this.attendancePercentage = attendancePercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 
     */
    public CourseClassDto(CourseClass courseClass) {
        this.courseClass = courseClass;
        this.batch = courseClass.getBatchNumber();
        this.quizPercentage = courseClass.getQuizPercentage();
        this.exercisePercentage = courseClass.getExercisePercentage();
        this.projectPercentage = courseClass.getProjectPercentage();
        this.attendancePercentage = courseClass.getAttendancePercentage();
        this.startDate = courseClass.getStartDate();
        this.endDate = courseClass.getEndDate();
    }

    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return the course
     */
    public CourseDto getCourse() {
        return course;
    }

    /**
     * @return the batch
     */
    public Integer getBatch() {
        return batch;
    }

    /**
     * @return the quizPercentage
     */
    public BigDecimal getQuizPercentage() {
        return quizPercentage;
    }

    /**
     * @return the exercisePercentage
     */
    public BigDecimal getExercisePercentage() {
        return exercisePercentage;
    }

    /**
     * @return the projectPercentage
     */
    public BigDecimal getProjectPercentage() {
        return projectPercentage;
    }

    /**
     * @return the attendancePercentage
     */
    public BigDecimal getAttendancePercentage() {
        return attendancePercentage;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the sme
     */
    public UserDto getSme() {
        return sme;
    }

    /**
     * @return the students
     */
    public List<UserDto> getStudents() {
        return students;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(CourseDto course) {
        this.course = course;
    }

    /**
     * @param batch the batch to set
     */
    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    /**
     * @param quizPercentage the quizPercentage to set
     */
    public void setQuizPercentage(BigDecimal quizPercentage) {
        this.quizPercentage = quizPercentage;
    }

    /**
     * @param exercisePercentage the exercisePercentage to set
     */
    public void setExercisePercentage(BigDecimal exercisePercentage) {
        this.exercisePercentage = exercisePercentage;
    }

    /**
     * @param projectPercentage the projectPercentage to set
     */
    public void setProjectPercentage(BigDecimal projectPercentage) {
        this.projectPercentage = projectPercentage;
    }

    /**
     * @param attendancePercentage the attendancePercentage to set
     */
    public void setAttendancePercentage(BigDecimal attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param sme the sme to set
     */
    public void setSme(UserDto sme) {
        this.sme = sme;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<UserDto> students) {
        this.students = students;
    }

    @JsonIgnore
    public CourseClassDto withStatus() {
        if (this.courseClass != null) {
            if (courseClass.isActive()) {
                this.status = "Active";
            } else {
                this.status = "Inactive";
            }
        }
        return this;
    }

    @JsonIgnore
    public CourseClassDto withCourse(Boolean withCourseId) {
        if (this.courseClass != null) {
            this.course = new CourseDto(this.courseClass.getCourse()).withStatus();
            if (withCourseId) {
                this.course.withId();
            }
        }
        return this;
    }

    @JsonIgnore
    public CourseClassDto withCourseCode() {
        if (this.courseClass != null) {
            this.courseCode = this.courseClass.getCourse().getCode();
        }
        return this;
    }

    @JsonIgnore
    public CourseClassDto withSME() {
        if (this.courseClass != null) {
            this.sme = new UserDto(this.courseClass.getSubjectMatterExpert()).withStatus();
        }
        return this;
    }

    @JsonIgnore
    public CourseClassDto withStudents(Boolean withScores) {
        if (this.courseClass != null) {
            this.students = this.courseClass.getStudents().stream().map(
                    student -> {
                        var studentDto = new UserDto(student).withoutRole();
                        if (withScores) {
                            studentDto.withScoresByClass(this.courseClass.getCourse().getCode(),
                                    this.courseClass.getBatchNumber());
                        }
                        return studentDto;
                    })
                    .collect(Collectors.toList());
        }
        return this;
    }

}
