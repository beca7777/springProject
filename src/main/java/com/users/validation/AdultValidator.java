package com.users.validation;

import com.users.util.SpringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.*;

public class AdultValidator implements ConstraintValidator<Adult, Instant> {
    @Override
    public void initialize(Adult constraintAnnotation) {
    }

    @Override
    public boolean isValid(Instant dateOfBirth, ConstraintValidatorContext context) {
        return SpringUtils.validateDateOfBirth(dateOfBirth);
    }
}

