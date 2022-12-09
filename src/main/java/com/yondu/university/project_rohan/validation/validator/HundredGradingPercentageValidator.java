package com.yondu.university.project_rohan.validation.validator;

import java.math.BigDecimal;

import org.springframework.beans.BeanWrapperImpl;

import com.yondu.university.project_rohan.validation.HundredGradingPercentage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HundredGradingPercentageValidator implements ConstraintValidator<HundredGradingPercentage, Object> {

    private String[] numberFields;

    @Override
    public void initialize(HundredGradingPercentage constraintAnnotation) {
        this.numberFields = constraintAnnotation.fieldNames();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        float total = 0.0f;
        for (String field : numberFields) {
            var bean = new BeanWrapperImpl(value);
            BigDecimal percent = (BigDecimal) bean.getPropertyValue(field);

            if (percent != null)
                total += percent.floatValue();
        }
        return total == 100.0f;
    }

}
