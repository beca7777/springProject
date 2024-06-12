package com.users.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.*;

public class AdultValidator implements ConstraintValidator<Adult, Instant> {

    @Override
    public void initialize(Adult constraintAnnotation) {
    }

    @Override
    public boolean isValid(Instant dateOfBirth, ConstraintValidatorContext context) {
        return com.users.util.AdultValidator.validateDateOfBirth(dateOfBirth);
    }
}

