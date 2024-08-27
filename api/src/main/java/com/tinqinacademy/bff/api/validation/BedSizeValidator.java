package com.tinqinacademy.bff.api.validation;


import com.tinqinacademy.bff.api.model.BffBedSize;
import com.tinqinacademy.bff.api.validation.annotations.ValidBedSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BedSizeValidator implements ConstraintValidator<ValidBedSize, String> {
    @Override
    public void initialize(ValidBedSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }

        for (BffBedSize bffBedSize : BffBedSize.values()) {
            if (!bffBedSize.toString().isEmpty() && bffBedSize.toString().equals(s)) {
                return true;
            }
        }

        return false;
    }
}
