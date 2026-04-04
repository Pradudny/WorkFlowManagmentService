package com.company.Incident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.Incident;
import com.company.Incident.payload.IncidentCompletedMessage;
import com.company.Incident.repository.IncidentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidentKafkaConsumer {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "${incidentmanagement.topic.name}", groupId = "incident-group")
    public void consumeIncidentCompleted(IncidentCompletedMessage message) {
        log.info("Received incident completed message: {}", message);

        try {
            // Fetch the incident to get the creator's email
            Incident incident = incidentRepository.findById(message.getIncidentId())
                    .orElseThrow(() -> new RuntimeException("Incident not found: " + message.getIncidentId()));

            String creatorEmail = incident.getCreatedBy();
            if (creatorEmail != null && !creatorEmail.isBlank()) {
                log.info("Notifying incident creator {} for incident {}", creatorEmail, message.getIncidentId());
                notificationService.notifyIncidentCompleted(creatorEmail, message.getIncidentId(),
                        message.getTitle(), message.getStatus());
            } else {
                log.warn("No creator email found for incident {}", message.getIncidentId());
            }
        } catch (Exception e) {
            log.error("Error processing incident completed message: {}", e.getMessage(), e);
        }
    }
}