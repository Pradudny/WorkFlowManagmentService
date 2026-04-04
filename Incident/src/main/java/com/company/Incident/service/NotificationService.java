package com.company.Incident.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

	SseEmitter subscribe(String email);

	void notifyIncidentCompleted(String email, int incidentId, String title, String status);

	boolean hasActiveEmitters(String email);
}
