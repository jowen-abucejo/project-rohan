package com.yondu.university.project_rohan.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yondu.university.project_rohan.validation.UniqueCourseCode;

import jakarta.validation.constraints.NotBlank;

public class CourseRequest {
    private String id;

    @NotBlank(message = "Course code is required.")
    @Length(max = 32, message = "Course code maximum length is 32 characters only.")
    @UniqueCourseCode
    private String code;

    @NotBlank(message = "Course title is required.")
    @Length(max = 128, message = "Course title maximum length is 128 characters ony.")
    private String title;

    @NotBlank(message = "Course description is required.")
    private String description;

    @JsonInclude(Include.NON_EMPTY)
    private String status;

    /**
     * 
     */
    public CourseRequest() {
    }

    /**
     * @param code
     * @param title
     * @param description
     */
    public CourseRequest(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
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

}
