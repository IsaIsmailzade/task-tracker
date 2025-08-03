package com.isa.tasktrackerbackend.controller;

import com.isa.tasktrackerbackend.config.swagger.TaskControllerDoc;
import com.isa.tasktrackerbackend.dto.CurrentUserTasksDto;
import com.isa.tasktrackerbackend.model.User;
import com.isa.tasktrackerbackend.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class TaskController implements TaskControllerDoc {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<CurrentUserTasksDto>> getCurrentUserTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getCurrentUserTasks(user));
    }
}
