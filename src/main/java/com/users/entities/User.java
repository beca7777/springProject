package com.users.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity{

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    private Instant dateOfBirth;
}
