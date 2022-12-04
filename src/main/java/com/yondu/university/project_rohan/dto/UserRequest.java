package com.yondu.university.project_rohan.dto;

import org.hibernate.validator.constraints.Length;

import com.yondu.university.project_rohan.validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRequest {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is in invalid format")
    @Length(max = 128, message = "Email maximum length is 128 characters only.")
    @UniqueEmail
    private String email;

    @NotBlank(message = "First name is required.")
    @Length(max = 128, message = "First name maximum length is 128 characters only.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Length(max = 128, message = "Last name maximum length is 128 characters only.")
    private String lastName;

    @NotBlank(message = "Role is required.")
    @Pattern(regexp = "^(STUDENT|student|SUBJECT MATTER EXPERT|subject matter expert)$", message = "Role can either be 'STUDENT' or 'SUBJECT MATTER EXPERT' only (case-insensitive).")
    private String role;

    private boolean isActive;

    /**
     * 
     */
    public UserRequest() {
        this.isActive = true;
    }

    /**
     * @param email
     * @param firstName
     * @param lastName
     * @param role
     */
    public UserRequest(String email, String firstName, String lastName, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = true;
    }

    /**
     * @param email
     * @param firstName
     * @param lastName
     * @param role
     * @param isActive
     */
    public UserRequest(String email, String firstName, String lastName, String role, boolean isActive) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isActive = isActive;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the isActive
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
