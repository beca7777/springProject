package com.users.criteria;

import com.users.filters.InstantFilter;
import com.users.filters.StringFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCriteria {

    private StringFilter primaryEmail;

    private InstantFilter dateOfBirth;

    private StringFilter phoneNumber;

    private StringFilter secondaryEmail;
}
