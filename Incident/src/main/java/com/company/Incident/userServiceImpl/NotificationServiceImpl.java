package com.company.Incident.userServiceImpl;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.company.Incident.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter subscribe(String email) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.computeIfAbsent(email, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> {
            emitters.getOrDefault(email, new CopyOnWriteArrayList<>()).remove(emitter);
            log.info("SSE emitter completed for user: {}", email);
        });

        emitter.onTimeout(() -> {
            emitters.getOrDefault(email, new CopyOnWriteArrayList<>()).remove(emitter);
            log.info("SSE emitter timed out for user: {}", email);
        });

        emitter.onError((throwable) -> {
            emitters.getOrDefault(email, new CopyOnWriteArrayList<>()).remove(emitter);
            log.error("SSE emitter error for user: {}", email, throwable);
        });

        log.info("User {} subscribed to notifications", email);
        return emitter;
    }

    @Override
    public boolean hasActiveEmitters(String email) {
        CopyOnWriteArrayList<SseEmitter> userEmitters = emitters.get(email);
        return userEmitters != null && !userEmitters.isEmpty();
    }

    @Override
    public void notifyIncidentCompleted(String email, int incidentId, String title, String status) {
        CopyOnWriteArrayList<SseEmitter> userEmitters = emitters.get(email);
        if (userEmitters != null) {
            // Send raw incident data to frontend for custom notification construction
            String incidentData = String.format(
                    "{\"type\":\"INCIDENT_COMPLETED\",\"data\":{\"incidentId\":%d,\"title\":\"%s\",\"status\":\"%s\"}}",
                    incidentId, title, status);

            userEmitters.forEach(emitter -> {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(incidentData));
                    log.info("Sent incident data to user {} for incident {}", email, incidentId);
                } catch (IOException e) {
                    log.error("Failed to send incident data to user {}: {}", email, e.getMessage());
                    userEmitters.remove(emitter);
                }
            });
        } else {
            log.warn("No active emitters found for user: {}", email);
        }
    }
}