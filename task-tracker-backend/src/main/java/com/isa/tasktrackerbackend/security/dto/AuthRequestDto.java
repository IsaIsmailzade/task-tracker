package com.isa.tasktrackerbackend.security.dto;

import lombok.Value;

@Value
public class AuthRequestDto {
    String username;
    String password;
}
