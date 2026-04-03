package com.company.Incident.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.company.Incident.payload.IncidentCompletedMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidentKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${incidentmanagement.topic.name}")
    private String incidentTopic;

    public IncidentKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishIncidentCompleted(IncidentCompletedMessage message) {
        if (incidentTopic == null || incidentTopic.isBlank()) {
            log.error("Kafka publish failed: incidentTopic is not configured");
            return;
        }

        log.info("Publishing incident completed message to Kafka topic='{}' incidentId='{}' status='{}'",
                incidentTopic, message.getIncidentId(), message.getStatus());

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(incidentTopic,
                String.valueOf(message.getIncidentId()), message);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish Kafka message for incidentId='{}' to topic='{}'. Error: {}",
                        message.getIncidentId(), incidentTopic, ex.getMessage(), ex);
            } else {
                log.info("Kafka message published successfully for incidentId='{}' to topic='{}'",
                        message.getIncidentId(), incidentTopic);
            }
        });
    }
}
