package com.yondu.university.project_rohan.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    /**
     * @param email
     * @param password
     */
    public LoginRequest(String email, String password) {
        this.email = email.trim();
        this.password = password.trim();
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

}
