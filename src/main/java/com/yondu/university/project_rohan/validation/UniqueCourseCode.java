package com.yondu.university.project_rohan.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueCourseCodeValidator.class)
@Documented
public @interface UniqueCourseCode {
    String message() default "Course code already exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
