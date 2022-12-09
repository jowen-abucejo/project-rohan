package com.yondu.university.project_rohan.validation.validator;

import com.yondu.university.project_rohan.service.CourseService;
import com.yondu.university.project_rohan.validation.CourseCodeExists;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseCodeExistsValidator implements ConstraintValidator<CourseCodeExists, String> {

    private final CourseService courseService;

    /**
     * @param courseService
     */
    public CourseCodeExistsValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return this.courseService.isCodeExist(code);
    }

}
