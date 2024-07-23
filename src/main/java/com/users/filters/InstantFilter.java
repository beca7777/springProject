package com.users.filters;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InstantFilter {

    private Instant equals;

    private Instant greaterThan;

    private Instant lessThan;

    private Instant greaterThanOrEqualTo;

    private Instant lessThanOrEqualTo;
}
