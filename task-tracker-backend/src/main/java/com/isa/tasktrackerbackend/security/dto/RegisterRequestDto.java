package com.isa.tasktrackerbackend.security.dto;

import lombok.Value;

@Value
public class RegisterRequestDto {
    String username;
    String password;
}
