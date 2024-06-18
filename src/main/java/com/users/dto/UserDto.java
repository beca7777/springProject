package com.users.dto;

import com.users.validation.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    private String email;

    @Adult
    @NotNull(message = "Date of birth is mandatory")
    private Instant dateOfBirth;
}
