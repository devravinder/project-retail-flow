package com.paravar.retailflow.users.dto;

import java.util.Set;


public record UserCreateDto(
        String email,
        String firstName,
        String lastName,
        String phone,
        Boolean active,
        String password,
        Set<String> roleNames  // e.g. ["ROLE_USER", "ROLE_SELLER"]
) {
}