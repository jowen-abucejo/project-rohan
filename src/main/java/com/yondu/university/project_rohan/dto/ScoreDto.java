package com.yondu.university.project_rohan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScoreDto {
    @JsonInclude(Include.NON_EMPTY)
    private String id;

    private ActivityDto activity;

    private UserDto student;

    @Email
    @NotBlank(message = "Email cannot be empty.")
    @JsonInclude(Include.NON_EMPTY)
    private String email;

    @NotNull(message = "Score cannot be empty")
    private Integer score;

    /**
     * 
     */
    public ScoreDto() {
    }

    /**
     * @param id
     * @param email
     * @param score
     */
    public ScoreDto(String id, String email, Integer score) {
        this.id = id;
        this.email = email;
        this.score = score;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * @return the activity
     */
    public ActivityDto getActivity() {
        return activity;
    }

    /**
     * @return the student
     */
    public UserDto getStudent() {
        return student;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param score the score to set
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(ActivityDto activity) {
        this.activity = activity;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(UserDto student) {
        this.student = student;
    }

}
