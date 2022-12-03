package com.yondu.university.project_rohan.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Username cannot be empty.")
    private String username;

    @NotBlank(message = "Password cannot be empty.")
    private String password;

    /**
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username.trim();
        this.password = password.trim();
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
