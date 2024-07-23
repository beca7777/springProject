package com.users.filters;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StringFilter {

    private String equals;

    private String contains;

    private List<String> in;
}
