package com.ptaushanov.shop.util;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default
            "Invalid password! Password must be at least 6 characters long, " +
            "contain at least one digit, two uppercase letters, two lowercase " +
            "letters and one special symbol.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
