package com.users.validation;

import com.users.constants.RegexConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class EmailListValidator implements ConstraintValidator<ValidEmailList, List<String>> {

    private Pattern pattern;

    @Override
    public void initialize(ValidEmailList constraintAnnotation) {
        pattern = Pattern.compile(RegexConstants.EMAIL_REGEX);
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
