package com.yondu.university.project_rohan.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yondu.university.project_rohan.validation.validator.CountMinValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = CountMinValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CountMin {

    String message() default "Invalid min and max number";

    String start();

    String end();

    int min() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
