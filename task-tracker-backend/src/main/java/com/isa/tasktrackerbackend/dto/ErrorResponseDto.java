package com.isa.tasktrackerbackend.dto;

import java.util.Map;

public record ErrorResponseDto(int status, String message, Map<String, String> errors) {
    public ErrorResponseDto(int status, String message) {
        this(status, message, null);
    }
}
