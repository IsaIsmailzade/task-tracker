package com.isa.tasktrackeremailsender.service;

import com.isa.tasktrackeremailsender.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "${spring.kafka.topic}")
    public void consumeEmailSendingMessage(MessageDto messageDto) {
        mailSenderService.sendMail(messageDto);
    }
}
