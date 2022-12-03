package com.yondu.university.project_rohan.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank(message = "Email is required.")
    @Length(max = 128, message = "Email maximum length is 128 characters only.")
    private String email;

    @NotBlank(message = "First name is required.")
    @Length(max = 128, message = "First name maximum length is 128 characters only.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Length(max = 128, message = "Last name maximum length is 128 characters only.")
    private String lastName;

    @NotBlank(message = "Role is required.")
    @Length(max = 128, message = "Role maximum length is 64 characters only.")
    private String role;

    private boolean isActive;

    /**
     * 
     */
    public UserRequest() {
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
     * @return the email
     */
    public String getEmail() {
        return email;
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
        return firstName;
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
        return lastName;
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
        return role;
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
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
