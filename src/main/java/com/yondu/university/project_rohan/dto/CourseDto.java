package com.yondu.university.project_rohan.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yondu.university.project_rohan.entity.Course;
import com.yondu.university.project_rohan.validation.UniqueCourseCode;

import jakarta.validation.constraints.NotBlank;

public class CourseDto {
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 1)
    private String id;

    @NotBlank(message = "Course code is required.")
    @Length(max = 32, message = "Course code maximum length is 32 characters only.")
    @UniqueCourseCode
    @JsonProperty(index = 2)
    private String code;

    @NotBlank(message = "Course title is required.")
    @Length(max = 128, message = "Course title maximum length is 128 characters ony.")
    @JsonProperty(index = 3)
    private String title;

    @NotBlank(message = "Course description is required.")
    @JsonProperty(index = 4)
    private String description;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 5)
    private String status;

    @JsonIgnore
    private Course course;

    /**
     * 
     */
    public CourseDto() {
    }

    /**
     * @param code
     * @param title
     * @param description
     */
    public CourseDto(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    /**
     * @param course
     */
    public CourseDto(Course course) {
        this.course = course;
        this.code = course.getCode();
        this.title = course.getTitle();
        this.description = course.getDescription();
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public CourseDto withId() {
        if (this.course != null) {
            this.id = this.course.getId() + "";
        }
        return this;
    }

    @JsonIgnore
    public CourseDto withStatus() {
        if (this.course != null) {
            if (this.course.isActive()) {
                this.status = "Active";
            } else {
                this.status = "Inactive";
            }
        }
        return this;
    }

}
