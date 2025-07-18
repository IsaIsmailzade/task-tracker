package com.isa.tasktrackeremailsender;

import com.isa.tasktrackeremailsender.dto.MessageDto;
import com.isa.tasktrackeremailsender.service.KafkaConsumerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class KafkaConsumerServiceIT {
    @Container
    static final KafkaContainer container = new KafkaContainer(DockerImageName.parse("apache/kafka-native"));

    @MockitoSpyBean
    private KafkaConsumerService kafkaConsumerService;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);
    }

    @DisplayName("KafkaConsumer should successfully receive and process message from topic")
    @Test
    void givenMessage_whenSent_thenConsumerShouldReceiveIt() {
        try (KafkaProducer<Long, MessageDto> producer = new KafkaProducer<>(producerConfigs())) {
            MessageDto messageDto = MessageDto.builder()
                    .email("test@gmail.com")
                    .title("Test Title")
                    .message("Test message")
                    .build();

            ProducerRecord<Long, MessageDto> record = new ProducerRecord<>(topic, messageDto);
            producer.send(record);

            verify(kafkaConsumerService, timeout(5000)).consumeEmailSendingMessage(any(MessageDto.class));
        }
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}
