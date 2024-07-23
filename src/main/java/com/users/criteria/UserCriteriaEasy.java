package com.users.criteria;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserCriteriaEasy {

    private String email;

    private Instant dateOfBirth;
}
