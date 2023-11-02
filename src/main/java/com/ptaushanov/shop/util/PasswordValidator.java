package com.ptaushanov.shop.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // Regular expressions for the requirements
        String uppercaseRegex = ".*[A-Z].*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*[a-z].*";
        String digitRegex = ".*[0-9].*";
        String specialCharacterRegex = ".*[!@$^&*()_\\-=+?\\[\\]:].*";

        // Check if the password meets the requirements
        return password.length() >= 6
               && password.matches(uppercaseRegex)
               && password.matches(lowercaseRegex)
               && password.matches(digitRegex)
               && password.matches(specialCharacterRegex);
    }
}