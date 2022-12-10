package com.yondu.university.project_rohan.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(length = 11, nullable = false)
    private Integer maxScore;

    @Column(length = 11, nullable = false)
    private Integer minScore;

    @Column(nullable = false)
    private LocalDate schedule;

    @Column(nullable = false, length = 16)
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_id", nullable = false)
    private CourseClass courseClass;

    @CreatedBy
    @Column(nullable = false, updatable = false, length = 128)
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(nullable = false, length = 128)
    private String updatedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @JsonBackReference
    @OneToMany(mappedBy = "activity")
    private List<Score> scores = new ArrayList<>();

    /**
     * 
     */
    public Activity() {
    }

    /**
     * @param id
     * @param title
     * @param maxScore
     * @param minScore
     * @param schedule
     * @param type
     * @param courseClass
     * @param createdBy
     * @param createdAt
     * @param updatedBy
     * @param updatedAt
     * @param scores
     */
    public Activity(Integer id, String title, Integer maxScore, Integer minScore, LocalDate schedule, String type,
            CourseClass courseClass, String createdBy, LocalDateTime createdAt, String updatedBy,
            LocalDateTime updatedAt, List<Score> scores) {
        this.id = id;
        this.title = title;
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.schedule = schedule;
        this.type = type;
        this.courseClass = courseClass;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.scores = scores;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
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
     * @return the schedule
     */
    public LocalDate getSchedule() {
        return schedule;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the courseClass
     */
    public CourseClass getCourseClass() {
        return courseClass;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @return the updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the scores
     */
    public List<Score> getScores() {
        return scores;
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
    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * @param minScore the minScore to set
     */
    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }

    /**
     * @param schedule the schedule to set
     */
    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param courseClass the courseClass to set
     */
    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

}
