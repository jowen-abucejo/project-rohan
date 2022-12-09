package com.yondu.university.project_rohan.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yondu.university.project_rohan.validation.validator.HundredGradingPercentageValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = HundredGradingPercentageValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HundredGradingPercentage {

    String message() default "Total grading percentage must be 100.";

    String[] fieldNames() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
