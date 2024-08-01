package com.users.entities;

import com.users.config.SecondaryEmailsConvertor;
import com.users.validation.Adult;
import com.users.validation.ValidEmailList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    private String primaryEmail;

    @Convert(converter = SecondaryEmailsConvertor.class)
    @Column(columnDefinition = "json")
    @ValidEmailList
    private List<String> secondaryEmails;

    @Column(length = 15, nullable = false)
    @Pattern(
            regexp = "^(\\+4|)?(07[0-9]{8}|02[0-9]{8}|03[0-9]{8})$",
            message = "Invalid Romanian phone number"
    )
    private String phoneNumber;

    @Adult
    @Column(nullable = false)
    private Instant dateOfBirth;
}
