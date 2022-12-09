package com.yondu.university.project_rohan.dto;

import java.time.LocalDate;

public class ActivityDto {
    private String id;
    private String courseCode;
    private int batch;
    private String title;
    private int maxScore;
    private int minScore;
    private LocalDate date;

    /**
     * 
     */
    public ActivityDto() {
    }

    /**
     * @param courseCode
     * @param batch
     * @param title
     * @param maxScore
     * @param minScore
     * @param date
     */
    public ActivityDto(String courseCode, int batch, String title, int maxScore, int minScore, LocalDate date) {
        this.courseCode = courseCode;
        this.batch = batch;
        this.title = title;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.date = date;
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
    public int getBatch() {
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
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * @return the minScore
     */
    public int getMinScore() {
        return minScore;
    }

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
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

}
