package com.tinqinacademy.bff.api.validation.annotations;


import com.tinqinacademy.bff.api.validation.BathroomTypeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BathroomTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBathroomType {
    String message() default "Invalid bathroom type";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
