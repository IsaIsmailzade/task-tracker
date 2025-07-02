package com.isa.scheduler.service;

import com.isa.scheduler.config.SchedulerProperties;
import com.isa.scheduler.model.Task;
import com.isa.scheduler.model.TaskStatus;
import com.isa.scheduler.model.User;
import com.isa.scheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final SchedulerProperties schedulerProperties;

    public List<String> getUncompletedTasksByUser(User user) {
        return taskRepository.findAllByUser(user).stream()
                .filter(task -> task.getTaskStatus().equals(TaskStatus.PENDING)
                        && task.getCompletedAt() == null)
                .map(Task::getTitle)
                .limit(schedulerProperties.getTasksCountLimit())
                .toList();
    }

    public List<String> getCompletedTasksByUser(User user) {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return taskRepository.findAllByUser(user).stream()
                .filter(task -> task.getTaskStatus().equals(TaskStatus.COMPLETED)
                        && task.getCompletedAt() != null
                        && task.getCompletedAt().isAfter(yesterday))
                .map(Task::getTitle)
                .limit(schedulerProperties.getTasksCountLimit())
                .toList();
    }
}
