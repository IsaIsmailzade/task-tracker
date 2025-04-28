package com.isa.tasktrackerbackend.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "task-tracker-backend.jwt")
public class JwtProperties {
    private String secretKey;
    private Duration accessTokenExpiration;
}
