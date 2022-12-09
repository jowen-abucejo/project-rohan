package com.yondu.university.project_rohan.validation.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanWrapperImpl;

import com.yondu.university.project_rohan.validation.DayCountMin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DayCountMinValidator implements ConstraintValidator<DayCountMin, Object> {

    private String start;
    private String end;
    private int min;

    @Override
    public void initialize(DayCountMin constraintAnnotation) {
        this.start = constraintAnnotation.start();
        this.end = constraintAnnotation.end();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var bean = new BeanWrapperImpl(value);
        long daysBetween = 0;
        try {
            LocalDate startDate = (LocalDate) bean.getPropertyValue(this.start);
            LocalDate endDate = (LocalDate) bean.getPropertyValue(this.end);

            if (endDate != null)
                endDate = endDate.plusDays(1);

            daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        } catch (DateTimeParseException e) {
            return false;
        }
        return daysBetween >= min;
    }

}
