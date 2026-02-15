package com.paravar.retailflow.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
public class UserCreateDto {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean active;
    private Set<String> roleNames;  // e.g. ["ROLE_USER", "ROLE_SELLER"]
}