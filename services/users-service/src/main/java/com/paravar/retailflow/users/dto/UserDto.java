package com.paravar.retailflow.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserDto {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<String> roles;
    private Set<AddressResponseDto> addresses;
}