package com.yondu.university.project_rohan.entity;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(unique = true, nullable = false, length = 32)
    @NotBlank(message = "Course code is required.")
    @Length(max = 32, message = "Course code maximum length is 32 characters only.")
    private String code;

    @Column(nullable = false, length = 128)
    @NotBlank(message = "Course title is required.")
    @Length(max = 128, message = "Course title maximum length is 128 characters ony.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Course description is required.")
    private String description;

    private boolean isActive;

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

    /**
     * 
     */
    public Course() {
        this.isActive = true;
    }

    /**
     * @param id
     * @param code
     * @param title
     * @param description
     * @param isActive
     * @param createdBy
     * @param createdAt
     * @param updatedBy
     * @param updatedAt
     */
    public Course(Integer id, String code, String title, String description, boolean isActive, String createdBy,
            LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * @return the updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}