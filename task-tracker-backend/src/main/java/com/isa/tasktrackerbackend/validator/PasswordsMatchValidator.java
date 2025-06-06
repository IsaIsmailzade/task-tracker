package com.isa.tasktrackerbackend.validator;

import com.isa.tasktrackerbackend.annotation.PasswordsMatch;
import com.isa.tasktrackerbackend.security.dto.RegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterRequestDto> {
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequestDto registerRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        return registerRequestDto.getPassword().equals(registerRequestDto.getRepeatPassword());
    }
}
