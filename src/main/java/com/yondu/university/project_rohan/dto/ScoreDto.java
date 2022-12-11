package com.yondu.university.project_rohan.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yondu.university.project_rohan.entity.Score;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ScoreDto {
    @JsonInclude(Include.NON_EMPTY)
    private String id;

    private Map<String, ActivityDto> activity = new HashMap<>();

    @JsonInclude(Include.NON_EMPTY)
    private UserDto student;

    @Email
    @NotBlank(message = "Email cannot be empty.")
    @JsonInclude(Include.NON_EMPTY)
    private String email;

    @NotNull(message = "Score cannot be empty")
    private Integer score;

    @JsonIgnore
    private Score scoreEntity;

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
    public ScoreDto(String email, Integer score) {
        this.email = email;
        this.score = score;
    }

    /**
     * 
     */
    public ScoreDto(Score score) {
        this.scoreEntity = score;
        this.score = score.getScore();
        this.setActivity(new ActivityDto(score.getActivity()));
        this.student = new UserDto(score.getStudent()).withStatus();
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
    @JsonAnyGetter
    public Map<String, ActivityDto> getActivity() {
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
        this.activity.put(activity.getType().toLowerCase(), activity);
    }

    /**
     * @param student the student to set
     */
    public void setStudent(UserDto student) {
        this.student = student;
    }

    @JsonIgnore
    public ScoreDto withoutStudent() {
        this.student = null;
        return this;
    }

}
