package com.users.criteria;

import com.users.dto.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserCriteriaEasy {

    private String email;
    private Instant dateOfBirth;
    private PageRequestDto pageRequestDto = new PageRequestDto();;
}
