package com.isa.tasktrackerbackend.service;

import com.isa.tasktrackerbackend.config.KafkaTopicProperties;
import com.isa.tasktrackerbackend.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTopicProperties properties;
    private final KafkaTemplate<Long, MessageDto> kafkaTemplate;

    public void sendMessage(MessageDto messageDto) {
        String topic = properties.getTopic();
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Kafka topic isn't configured");
        }

        kafkaTemplate.send(topic, messageDto);
    }
}
