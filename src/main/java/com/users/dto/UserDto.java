package com.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
public class UserDto extends BaseDto{

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    private Instant dateOfBirth;
}
