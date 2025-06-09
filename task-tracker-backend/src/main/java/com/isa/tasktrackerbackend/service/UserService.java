package com.isa.tasktrackerbackend.service;

import com.isa.tasktrackerbackend.dto.CurrentUserDto;
import com.isa.tasktrackerbackend.exception.InvalidCredentialsException;
import com.isa.tasktrackerbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public CurrentUserDto getCurrentUser(UserDetails userDetails) {
        if (userDetails == null) {
            throw new InvalidCredentialsException("User details not provided");
        }
        return userRepository.findByEmail(userDetails.getUsername())
                .map(user -> new CurrentUserDto(
                        user.getId(),
                        user.getEmail()
                ))
                .orElseThrow(() -> new InvalidCredentialsException("Invalid or expired token"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }
}
