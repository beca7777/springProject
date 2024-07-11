package com.users.criteria;

import com.users.dto.PageRequestDto;
import com.users.filters.InstantFilter;
import com.users.filters.StringFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCriteria {

    private StringFilter email;
    private InstantFilter dateOfBirth;
    private PageRequestDto pageRequestDto;
}
