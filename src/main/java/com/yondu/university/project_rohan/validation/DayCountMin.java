package com.yondu.university.project_rohan.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yondu.university.project_rohan.validation.validator.DayCountMinValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DayCountMinValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DayCountMin {

    String message() default "Invalid date range given.";

    String start();

    String end();

    int min() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
