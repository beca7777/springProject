package com.users.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
public abstract class AuditorDto {

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private String createdBy;

    private String updatedBy;
}
