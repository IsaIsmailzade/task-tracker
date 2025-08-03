package com.isa.tasktrackerbackend.security.auth;

import com.isa.tasktrackerbackend.config.swagger.AuthControllerDoc;
import com.isa.tasktrackerbackend.security.dto.AuthRequestDto;
import com.isa.tasktrackerbackend.security.dto.RegisterRequestDto;
import com.isa.tasktrackerbackend.security.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {
    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> register(@Validated @RequestBody RegisterRequestDto request) {
        ResponseDto response = authService.register(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken())
                .body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto> login(@Validated @RequestBody AuthRequestDto request) {
        ResponseDto response = authService.authenticate(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.accessToken())
                .body(response);
    }
}
