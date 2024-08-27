package com.tinqinacademy.bff.api.validation;


import com.tinqinacademy.bff.api.model.BffBathroomType;
import com.tinqinacademy.bff.api.validation.annotations.ValidBathroomType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class BathroomTypeValidator implements ConstraintValidator<ValidBathroomType, String> {
    @Override
    public void initialize(ValidBathroomType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        for (BffBathroomType bffBathroomType : BffBathroomType.values()) {
            if (!bffBathroomType.toString().isEmpty() && bffBathroomType.toString().equals(s)) {
                return true;
            }
        }

        return false;
    }
}
