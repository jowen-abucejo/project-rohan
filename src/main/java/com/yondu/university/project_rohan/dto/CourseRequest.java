package com.yondu.university.project_rohan.dto;

import org.hibernate.validator.constraints.Length;

import com.yondu.university.project_rohan.validation.UniqueCourseCode;

import jakarta.validation.constraints.NotBlank;

public class CourseRequest {
    @NotBlank(message = "Course code is required.")
    @Length(max = 32, message = "Course code maximum length is 32 characters only.")
    @UniqueCourseCode
    private String code;

    @NotBlank(message = "Course title is required.")
    @Length(max = 128, message = "Course title maximum length is 128 characters ony.")
    private String title;

    @NotBlank(message = "Course description is required.")
    private String description;

    private boolean isActive;

    /**
     * 
     */
    public CourseRequest() {
        this.isActive = true;
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
        this.isActive = true;
    }

    /**
     * @param code
     * @param title
     * @param description
     * @param isActive
     */
    public CourseRequest(String code, String title, String description, boolean isActive) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
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
        return title;
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
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
