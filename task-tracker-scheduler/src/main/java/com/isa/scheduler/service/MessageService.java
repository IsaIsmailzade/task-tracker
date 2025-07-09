package com.isa.scheduler.service;

import com.isa.scheduler.config.SchedulerProperties;
import com.isa.scheduler.dto.MessageDto;
import com.isa.scheduler.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final TaskService taskService;
    private final SchedulerProperties schedulerProperties;

    public MessageDto createTotalMessage(User user) {
        List<String> uncompletedTasks = taskService.getUncompletedTasksByUser(user);
        List<String> completedTasks = taskService.getCompletedTasksByUser(user);

        boolean hasUncompletedTasks = !uncompletedTasks.isEmpty();
        boolean hasCompletedTasks = !completedTasks.isEmpty();

        if (hasCompletedTasks && hasUncompletedTasks) {
            return createAllTasksMessage(user);
        } else if (hasCompletedTasks) {
            return createCompletedTasksMessage(user);
        } else if (hasUncompletedTasks) {
            return createUncompletedTasksMessage(user);
        } else {
            return buildMessage(user, new StringBuilder("У вас нет задач."));
        }
    }

    private MessageDto createCompletedTasksMessage(User user) {
        List<String> tasks = taskService.getCompletedTasksByUser(user);
        int completedTasksCount = tasks.size();

        StringBuilder message = new StringBuilder();
        if (completedTasksCount >= 1) {
            message.append("За сегодня вы выполнили ")
                    .append(completedTasksCount)
                    .append(" задач.")
                    .append("\n\n")
                    .append("Задачи:")
                    .append("\n");
            formatTasksList(tasks, message);

        }

        return buildMessage(user, message);
    }

    private MessageDto createUncompletedTasksMessage(User user) {
        List<String> tasks = taskService.getUncompletedTasksByUser(user);
        int uncompletedTasksCount = tasks.size();

        StringBuilder message = new StringBuilder();
        if (uncompletedTasksCount == 1) {
            message.append("У вас осталась ")
                    .append(uncompletedTasksCount)
                    .append(" несделанная задача.")
                    .append("\n\n")
                    .append("Задача:")
                    .append("\n");
            formatTasksList(tasks, message);
        } else if (uncompletedTasksCount > 1) {
            message.append("У вас осталось ")
                    .append(uncompletedTasksCount)
                    .append(" несделанных задач.")
                    .append("\n\n")
                    .append("Задачи:")
                    .append("\n");
            formatTasksList(tasks, message);
        } else {
            message.append("Отличная работа! Все задачи на сегодня выполнены.");
        }

        return buildMessage(user, message);
    }

    private MessageDto createAllTasksMessage(User user) {
        List<String> completedTasks = taskService.getCompletedTasksByUser(user);
        List<String> uncompletedTasks = taskService.getUncompletedTasksByUser(user);
        int completedTasksCount = completedTasks.size();
        int uncompletedTasksCount = uncompletedTasks.size();

        StringBuilder message = new StringBuilder();
        message.append("За сегодня вы выполнили ")
                .append(completedTasksCount)
                .append(" задач.")
                .append("\n\n")
                .append("Задачи:")
                .append("\n");
        formatTasksList(completedTasks, message);

        message.append("\n\n")
                .append("У вас осталось ")
                .append(uncompletedTasksCount)
                .append(" несделанных задач.")
                .append("\n\n")
                .append("Задачи:")
                .append("\n");
        formatTasksList(uncompletedTasks, message);

        return buildMessage(user, message);
    }

    private MessageDto buildMessage(User user, StringBuilder message) {
        return MessageDto.builder()
                .email(user.getEmail())
                .title(schedulerProperties.getMessageTitle())
                .message(message.toString())
                .build();
    }

    private void formatTasksList(List<String> tasks, StringBuilder message) {
        tasks.stream()
                .limit(schedulerProperties.getTasksCountLimit())
                .toList()
                .forEach(task -> message
                        .append("- ")
                        .append(task)
                        .append("\n"));
    }
}