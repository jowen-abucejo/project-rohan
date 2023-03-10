package com.yondu.university.project_rohan.validation.validator;

import com.yondu.university.project_rohan.service.CourseService;
import com.yondu.university.project_rohan.validation.UniqueCourseCode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueCourseCodeValidator implements ConstraintValidator<UniqueCourseCode, String> {

    private final CourseService courseService;

    /**
     * @param courseService
     */
    public UniqueCourseCodeValidator(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return !this.courseService.isCodeExist(code);
    }

}
