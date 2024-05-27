package com.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
public class OrderDto extends BaseDto{

    private Instant deliveryDate;

    private Instant orderDate;

    private UserDto user;
}
