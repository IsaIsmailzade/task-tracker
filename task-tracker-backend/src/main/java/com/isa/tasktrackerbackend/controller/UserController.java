package com.isa.tasktrackerbackend.controller;

import com.isa.tasktrackerbackend.config.swagger.UserControllerDoc;
import com.isa.tasktrackerbackend.dto.CurrentUserDto;
import com.isa.tasktrackerbackend.model.User;
import com.isa.tasktrackerbackend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class UserController implements UserControllerDoc {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<CurrentUserDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getCurrentUser(user));
    }
}
