package com.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto extends AuditorDto {

    private Long id;

    @NotNull(message = "Delivery date in mandatory.")
    private Instant deliveryDate;

    @NotNull(message = "Order date in mandatory.")
    private Instant orderDate;

    @NotNull(message = "User is mandatory")
    private UserDto user;
}
