package com.paravar.retailflow.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserUpdateDto {

    private String firstName;
    private String lastName;
    private String phone;
    private Boolean active;
    private Set<String> roleNames;
}