package com.yondu.university.project_rohan.validation.validator;

import com.yondu.university.project_rohan.service.UserService;
import com.yondu.university.project_rohan.validation.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    /**
     * @param userService
     */
    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !this.userService.isEmailExists(email);
    }

}
