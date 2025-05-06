package com.isa.tasktrackerbackend.security.dto;

import java.util.Map;

public record ResponseDto(String accessToken, String message, Map<String, String> errors) {
    public ResponseDto(String accessToken, String message) {
        this(accessToken, message, null);
    }
}
