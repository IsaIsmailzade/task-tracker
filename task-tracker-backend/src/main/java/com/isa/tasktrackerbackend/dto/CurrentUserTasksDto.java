package com.isa.tasktrackerbackend.dto;

import com.isa.tasktrackerbackend.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrentUserTasksDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private LocalDateTime completedAt;
}
