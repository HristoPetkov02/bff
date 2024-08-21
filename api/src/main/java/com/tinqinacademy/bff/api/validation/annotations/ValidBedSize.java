package com.tinqinacademy.bff.api.validation.annotations;


import com.tinqinacademy.bff.api.validation.BedSizeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BedSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBedSize {
    String message() default "Invalid bed size";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
