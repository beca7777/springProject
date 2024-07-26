package com.users.dto;

import com.users.validation.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto extends AuditorDto {

    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    private String primaryEmail;

    private List<String> secondaryEmail;

    @NotNull(message = "Phone number is mandatory")
    @Pattern(
            regexp = "^(\\+4|)?(07[0-9]{8}|02[0-9]{8}|03[0-9]{8})$",
            message = "Invalid Romanian phone number"
    )
    private String phoneNumber;

    @Adult
    @NotNull(message = "Date of birth is mandatory")
    private Instant dateOfBirth;
}
