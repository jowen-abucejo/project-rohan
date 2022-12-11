package com.yondu.university.project_rohan.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.validation.CountMin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@CountMin(start = "minScore", end = "maxScore", min = 1, message = "Invalid min and max score")
public class ActivityDto {
    private String id;

    @JsonProperty(value = "course_code")
    private String courseCode;

    private int batch;

    @NotBlank(message = "Title cannot be empty.")
    private String title;

    @NotNull(message = "Max score is required.")
    @JsonProperty(value = "max_score")
    private Integer maxScore;

    @NotNull(message = "Min score is required.")
    @JsonProperty(value = "min_score")
    private Integer minScore;

    @NotNull(message = "Date is required.")
    private LocalDate date;

    @JsonIgnore
    private String type;

    @JsonIgnore
    private Activity activity;

    /**
     * 
     */
    public ActivityDto() {
    }

    /**
     * @param title
     * @param maxScore
     * @param minScore
     * @param date
     */
    public ActivityDto(String title, Integer maxScore, Integer minScore, LocalDate date) {
        this.title = title;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.date = date;
    }

    /**
     * @param activity
     */
    public ActivityDto(Activity activity) {
        this.activity = activity;
        this.id = activity.getId() + "";
        this.courseCode = activity.getCourseClass().getCourse().getCode();
        this.batch = activity.getCourseClass().getBatchNumber();
        this.title = activity.getTitle();
        this.maxScore = activity.getMaxScore();
        this.minScore = activity.getMinScore();
        this.date = activity.getSchedule();
        this.type = activity.getType();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the maxScore
     */
    public Integer getMaxScore() {
        return maxScore;
    }

    /**
     * @return the minScore
     */
    public Integer getMinScore() {
        return minScore;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * @param batch the batch to set
     */
    public void setBatch(int batch) {
        this.batch = batch;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param maxScore the maxScore to set
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * @param minScore the minScore to set
     */
    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
