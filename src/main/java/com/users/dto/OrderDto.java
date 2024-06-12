package com.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private Instant deliveryDate;

    private Instant orderDate;

    private UserDto user;
}
