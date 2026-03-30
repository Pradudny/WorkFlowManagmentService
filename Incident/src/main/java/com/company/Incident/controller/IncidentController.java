package com.company.Incident.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.IncidentDTO;
import com.company.Incident.service.IncidentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@Validated
@RequestMapping("/api/incidents")
@Slf4j
public class IncidentController {

	@Autowired
	private IncidentService incidentService;

	@PostMapping("/add-incident")
	public ResponseEntity<IncidentDTO> createIncident(@RequestBody @Valid IncidentDTO incidentDTO,
			HttpServletRequest request) {
		String email = extractUserEmailHeader(request);

		IncidentDTO created = incidentService.createIncident(incidentDTO, email);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	private String extractUserEmailHeader(HttpServletRequest request) {
		String email = request.getHeader("X-User-Email");
		return email;
	}

	@GetMapping("/incident-details")
	public ResponseEntity<IncidentDTO> getIncidentById(
			@RequestParam(value = "incidentId") @Min(value = 1, message = "Incident ID must be a positive integer") int incidentId) {
		IncidentDTO incident = incidentService.getIncidentById(incidentId);
		return ResponseEntity.ok(incident);
	}

	@GetMapping("/incident-list")
	public ResponseEntity<List<IncidentDTO>> getAllIncidents() {
		List<IncidentDTO> incidents = incidentService.getAllIncidents();
		return ResponseEntity.ok(incidents);
	}

	@PutMapping("/update-incident")
	public ResponseEntity<IncidentDTO> updateIncident(
			@RequestParam(value = "incidentId") @Min(value = 1, message = "Incident ID must be a positive integer") int incidentId,
			@RequestBody IncidentDTO incidentDTO) {
		IncidentDTO updated = incidentService.updateIncident(incidentId, incidentDTO);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete-incident")
	public ResponseEntity<String> deleteIncident(
			@RequestParam @Min(value = 1, message = "Incident ID must be a positive integer") int incidentId) {
		incidentService.deleteIncident(incidentId);
		return ResponseEntity.ok("Incident deleted successfully");
	}
}
