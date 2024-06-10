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
        if (dateOfBirth == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = LocalDateTime.ofInstant(dateOfBirth, ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(birthDate, today).getYears();

        return age >= 18;
    }
}

