package com.isa.tasktrackerbackend.service;

import com.isa.tasktrackerbackend.dto.CurrentUserTasksDto;
import com.isa.tasktrackerbackend.exception.InvalidCredentialsException;
import com.isa.tasktrackerbackend.exception.ResourceNotFoundException;
import com.isa.tasktrackerbackend.mapper.TaskMapper;
import com.isa.tasktrackerbackend.model.User;
import com.isa.tasktrackerbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<CurrentUserTasksDto> getCurrentUserTasks(User user) {
        try {
            List<CurrentUserTasksDto> tasks = taskRepository.findTasksByUser_Id(user.getId())
                    .stream()
                    .map(taskMapper::mapTo)
                    .toList();

            if (tasks.isEmpty()) {
                throw new ResourceNotFoundException("The user has no tasks");
            }

            return tasks;
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException("The user isn't authorized or his token has expired");
        }
    }
}
