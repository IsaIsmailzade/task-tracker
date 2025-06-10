package com.isa.tasktrackerbackend.mapper;

import com.isa.tasktrackerbackend.dto.CurrentUserTasksDto;
import com.isa.tasktrackerbackend.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements Mapper<Task, CurrentUserTasksDto> {
    @Override
    public CurrentUserTasksDto mapTo(Task task) {
        return new CurrentUserTasksDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus(),
                task.getCompletedAt()
        );
    }
}
