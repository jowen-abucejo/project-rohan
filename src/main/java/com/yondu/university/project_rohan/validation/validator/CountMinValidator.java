package com.yondu.university.project_rohan.validation.validator;

import org.springframework.beans.BeanWrapperImpl;

import com.yondu.university.project_rohan.validation.CountMin;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountMinValidator implements ConstraintValidator<CountMin, Object> {

    private String start;
    private String end;
    private int min;

    @Override
    public void initialize(CountMin constraintAnnotation) {
        this.start = constraintAnnotation.start();
        this.end = constraintAnnotation.end();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var bean = new BeanWrapperImpl(value);
        Integer minNumber = (Integer) bean.getPropertyValue(this.start);
        Integer maxNumber = (Integer) bean.getPropertyValue(this.end);

        if (minNumber == null || maxNumber == null)
            return false;

        return maxNumber - minNumber >= min;
    }

}
