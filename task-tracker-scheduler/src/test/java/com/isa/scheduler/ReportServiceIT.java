package com.isa.scheduler;

import com.isa.scheduler.dto.MessageDto;
import com.isa.scheduler.service.KafkaProducerService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class ReportServiceIT {
    @Container
    static final KafkaContainer container = new KafkaContainer(DockerImageName.parse("apache/kafka-native"));

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.group-id}")
    private String groupId;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.kafka.bootstrap-servers", container::getBootstrapServers);
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MessageDto.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

    @Test
    void givenKafkaDockerContainer_whenSendingMessage_thenMessageReceived() {
        try (KafkaConsumer<Long, MessageDto> consumer = new KafkaConsumer<>(consumerConfigs())) {
            MessageDto messageDto = MessageDto.builder()
                    .email("test@gmail.com")
                    .title("Test Title")
                    .message("Test message")
                    .build();

            consumer.subscribe(List.of(topic));
            kafkaProducerService.sendMessage(messageDto);
            ConsumerRecord<Long, MessageDto> record = KafkaTestUtils.getSingleRecord(consumer, topic);

            assertThat(record).isNotNull();
            assertThat(record.value().getEmail()).isEqualTo("test@gmail.com");
            assertThat(record.value().getTitle()).isEqualTo("Test Title");
            assertThat(record.value().getMessage()).isEqualTo("Test message");
        }
    }
}
