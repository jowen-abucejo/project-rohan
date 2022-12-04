package com.yondu.university.project_rohan.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import org.hibernate.validator.constraints.Length;
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
import jakarta.validation.constraints.NotBlank;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Integer id;

    @Column(unique = true, nullable = false, length = 128)
    @NotBlank(message = "Email is required.")
    @Length(max = 128, message = "Email maximum length is 128 characters only.")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 128)
    @NotBlank(message = "First name is required.")
    @Length(max = 128, message = "First name maximum length is 128 characters only.")
    private String firstName;

    @Column(nullable = false, length = 128)
    @NotBlank(message = "Last name is required.")
    @Length(max = 128, message = "Last name maximum length is 128 characters only.")
    private String lastName;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();

    /**
     * 
     */
    public User() {
        this.isActive = true;
    }

    /**
     * @param id
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @param isActive
     * @param createdBy
     * @param createdAt
     * @param updatedBy
     * @param updatedAt
     * @param roles
     */
    public User(Integer id, String email, String password, String firstName, String lastName, boolean isActive,
            String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
            HashSet<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.roles = roles;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
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
    public LocalDateTime getCreated_at() {
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
    public LocalDateTime getUpdated_at() {
        return this.updatedAt;
    }

    /**
     * @return the roles
     */
    public Collection<Role> getRoles() {
        return this.roles;
    }

    /**
     * 
     * @param id the id to set
     */
    public void setId(Integer id) {
        if (this.id == null || this.id == 0)
            this.id = id;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

}
