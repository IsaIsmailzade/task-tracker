package com.isa.tasktrackerbackend.security.auth;

import com.isa.tasktrackerbackend.dto.MessageDto;
import com.isa.tasktrackerbackend.exception.InvalidCredentialsException;
import com.isa.tasktrackerbackend.exception.ResourceNotFoundException;
import com.isa.tasktrackerbackend.exception.UserAlreadyExistsException;
import com.isa.tasktrackerbackend.model.Role;
import com.isa.tasktrackerbackend.model.User;
import com.isa.tasktrackerbackend.repository.UserRepository;
import com.isa.tasktrackerbackend.security.dto.AuthRequestDto;
import com.isa.tasktrackerbackend.security.dto.RegisterRequestDto;
import com.isa.tasktrackerbackend.security.dto.ResponseDto;
import com.isa.tasktrackerbackend.security.jwt.JwtService;
import com.isa.tasktrackerbackend.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public ResponseDto register(RegisterRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);

        MessageDto messageDto = new MessageDto(
                request.getEmail(),
                "TaskTracker",
                "Thank you for registering on our website!"
        );

        kafkaProducerService.sendMessage(messageDto);
        return new ResponseDto(accessToken, "Registered successfully");
    }

    public ResponseDto authenticate(AuthRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);

        return new ResponseDto(accessToken, "Authorized successfully");
    }
}
