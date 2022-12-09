package com.yondu.university.project_rohan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "class")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CourseClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_code", referencedColumnName = "code", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer batchNumber;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal quizPercentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal exercisePercentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal projectPercentage;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal attendancePercentage;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sme_email", referencedColumnName = "email", nullable = false)
    private User subjectMatterExpert;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "student_class", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> students = new HashSet<>();

    /**
     * 
     */
    public CourseClass() {
        this.isActive = true;
    }

    /**
     * @param id
     * @param course
     * @param batchNumber
     * @param quizPercentage
     * @param exercisePercentage
     * @param projectPercentage
     * @param attendancePercentage
     * @param startDate
     * @param endDate
     * @param isActive
     * @param subjectMatterExpert
     * @param createdBy
     * @param createdAt
     * @param updatedBy
     * @param updatedAt
     * @param students
     */
    public CourseClass(Integer id, Course course, Integer batchNumber, BigDecimal quizPercentage,
            BigDecimal exercisePercentage, BigDecimal projectPercentage, BigDecimal attendancePercentage,
            LocalDate startDate, LocalDate endDate, boolean isActive, User subjectMatterExpert, String createdBy,
            LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt, Set<User> students) {
        this.id = id;
        this.course = course;
        this.batchNumber = batchNumber;
        this.quizPercentage = quizPercentage;
        this.exercisePercentage = exercisePercentage;
        this.projectPercentage = projectPercentage;
        this.attendancePercentage = attendancePercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.subjectMatterExpert = subjectMatterExpert;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.students = students;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @return the batchNumber
     */
    public Integer getBatchNumber() {
        return batchNumber;
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
     * @return the isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @return the subjectMatterExpert
     */
    public User getSubjectMatterExpert() {
        return subjectMatterExpert;
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
     * @return the students
     */
    public Set<User> getStudents() {
        return students;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * @param batchNumber the batchNumber to set
     */
    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
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
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @param subjectMatterExpert the subjectMatterExpert to set
     */
    public void setSubjectMatterExpert(User subjectMatterExpert) {
        this.subjectMatterExpert = subjectMatterExpert;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(Set<User> students) {
        this.students = students;
    }

}
