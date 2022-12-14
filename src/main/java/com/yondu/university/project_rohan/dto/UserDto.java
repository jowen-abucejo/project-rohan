package com.yondu.university.project_rohan.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yondu.university.project_rohan.entity.Activity;
import com.yondu.university.project_rohan.entity.Score;
import com.yondu.university.project_rohan.entity.User;
import com.yondu.university.project_rohan.validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDto {
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is in invalid format")
    @Length(max = 128, message = "Email maximum length is 128 characters only.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@yondu.com$", message = "Invalid yondu email provided.")
    @UniqueEmail
    @JsonProperty(index = 1)
    private String email;

    @NotBlank(message = "First name is required.")
    @Length(max = 128, message = "First name maximum length is 128 characters only.")
    @JsonProperty(value = "first_name", index = 2)
    private String firstName;

    @JsonProperty(value = "last_name", index = 3)
    @NotBlank(message = "Last name is required.")
    @Length(max = 128, message = "Last name maximum length is 128 characters only.")
    private String lastName;

    @NotBlank(message = "Role is required.")
    @Pattern(regexp = "^(STUDENT|student|SUBJECT MATTER EXPERT|subject matter expert)$", message = "Role can either be 'STUDENT' or 'SUBJECT MATTER EXPERT' only (case-insensitive).")
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 4)
    private String role;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 5)
    private String password;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty(index = 6)
    private String status;

    @JsonInclude(Include.NON_EMPTY)
    HashMap<String, List<ScoreDto>> scores;

    @JsonIgnore
    private User user;

    /**
     * 
     */
    public UserDto() {
    }

    /**
     * @param email
     * @param firstName
     * @param lastName
     * @param role
     */
    public UserDto(String email, String firstName, String lastName, String role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    /**
     * @param user
     */
    public UserDto(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = String.join(",",
                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
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
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonIgnore
    public UserDto withoutRole() {
        this.role = "";
        return this;
    }

    @JsonIgnore
    public UserDto withStatus() {
        if (this.user != null) {
            if (this.user.isActive()) {
                this.status = "Active";
            } else {
                this.status = "Inactive";
            }
        }
        return this;
    }

    public HashMap<String, List<ScoreDto>> getScores() {
        return this.scores;
    }

    @JsonIgnore
    public UserDto withPassword() {
        if (this.user != null) {
            this.password = this.user.getPassword();
        }
        return this;
    }

    @JsonIgnore
    public UserDto withScoresByClass(String courseCode, Integer batch) {
        if (this.user != null) {
            HashMap<String, List<ScoreDto>> activityScores = new HashMap<>();
            for (Score score : this.user.getScores()) {
                Activity activity = score.getActivity();
                if (activity.getCourseClass().getCourse().getCode().equals(courseCode)
                        && activity.getCourseClass().getBatchNumber() == batch) {
                    ScoreDto scoreDto = new ScoreDto(score).withoutStudent();

                    String type = score.getActivity().getType().toLowerCase();
                    if (type.endsWith("z"))
                        type += "zes";
                    else
                        type += "s";

                    List<ScoreDto> scoreList = activityScores.getOrDefault(type, new ArrayList<ScoreDto>());
                    scoreList.add(scoreDto);
                    activityScores.put(type, scoreList);
                }
            }
            this.scores = activityScores;
        }

        return this;
    }

}
