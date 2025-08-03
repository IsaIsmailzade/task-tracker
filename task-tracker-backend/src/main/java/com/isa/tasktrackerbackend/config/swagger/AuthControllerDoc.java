package com.isa.tasktrackerbackend.config.swagger;

import com.isa.tasktrackerbackend.dto.ErrorResponseDto;
import com.isa.tasktrackerbackend.security.dto.AuthRequestDto;
import com.isa.tasktrackerbackend.security.dto.RegisterRequestDto;
import com.isa.tasktrackerbackend.security.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Auth operations.")
public interface AuthControllerDoc {

    @ApiResponses({
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "409",
                    description = "Conflict",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))
            )
    })
    @Operation(summary = "Register new user")
    public ResponseEntity<ResponseDto> register(@Validated @RequestBody RegisterRequestDto request);

    @ApiResponses({
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))
            )
    })
    @Operation(summary = "Log in to account")
    public ResponseEntity<ResponseDto> login(@Validated @RequestBody AuthRequestDto request);
}
