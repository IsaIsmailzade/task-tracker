package com.isa.tasktrackeremailsender.service;

import com.isa.tasktrackeremailsender.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void sendMail(MessageDto messageDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(messageDto.getEmail());
        mailMessage.setSubject(messageDto.getTitle());
        mailMessage.setText(messageDto.getMessage());
        mailSender.send(mailMessage);
    }
}
