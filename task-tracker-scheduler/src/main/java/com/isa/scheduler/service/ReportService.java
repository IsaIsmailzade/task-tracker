package com.isa.scheduler.service;

import com.isa.scheduler.dto.MessageDto;
import com.isa.scheduler.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final KafkaProducerService kafkaProducerService;
    private final MessageService messageService;
    private final UserService userService;

//    @Scheduled(fixedDelay = 120000) // for method testing
    @Scheduled(cron = "${scheduler.cron-new}")
    public void sendReport() {
        List<User> users = userService.findAll();

        for (User user : users) {
            MessageDto messageDto = messageService.createTotalMessage(user);
            if (messageDto != null) {
                kafkaProducerService.sendMessage(messageDto);
            }
        }
    }
}
