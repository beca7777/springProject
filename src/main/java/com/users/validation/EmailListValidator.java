package com.users.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class EmailListValidator implements ConstraintValidator<ValidEmailList, List<String>> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private Pattern pattern;

    @Override
    public void initialize(ValidEmailList constraintAnnotation) {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public boolean isValid(List<String> emails, ConstraintValidatorContext context) {
        if (emails == null) {
            return true;
        }
        for (String email : emails) {
            if (!pattern.matcher(email).matches()) {
                return false;
            }
        }
        return true;
    }
}
