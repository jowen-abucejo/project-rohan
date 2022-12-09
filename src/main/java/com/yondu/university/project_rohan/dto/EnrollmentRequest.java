package com.yondu.university.project_rohan.dto;

import jakarta.validation.constraints.NotBlank;

public class EnrollmentRequest {
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    /**
     * 
     */
    public EnrollmentRequest() {
    }

    /**
     * @param email
     */
    public EnrollmentRequest(String email) {
        this.email = email;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
