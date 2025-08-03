package com.isa.tasktrackerbackend.config.swagger;

import com.isa.tasktrackerbackend.dto.CurrentUserTasksDto;
import com.isa.tasktrackerbackend.dto.ErrorResponseDto;
import com.isa.tasktrackerbackend.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "Tasks", description = "Operations on tasks of the current user.")
@SecurityRequirement(name = "BearerAuth")
public interface TaskControllerDoc {

    @ApiResponses({
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
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
                    content = @Content(schema = @Schema(implementation = CurrentUserTasksDto.class))
            )
    })
    @Operation(summary = "Get tasks of the current user")
    public ResponseEntity<List<CurrentUserTasksDto>> getCurrentUserTasks(@AuthenticationPrincipal User user);
}
