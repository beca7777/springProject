package com.users.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdultValidator {

    public static boolean validateDateOfBirth(Instant ageOfBirth) {
        if (ageOfBirth == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalDate birthDate = LocalDateTime.ofInstant(ageOfBirth, ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(birthDate, today).getYears();
        return age >= 18;
    }
}
