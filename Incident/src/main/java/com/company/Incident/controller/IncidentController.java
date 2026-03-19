package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.IncidentDTO;
import com.company.Incident.service.IncidentService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

	@Autowired
	private IncidentService incidentService;

	@PostMapping
	public ResponseEntity<IncidentDTO> createIncident(@RequestBody IncidentDTO incidentDTO) {
		IncidentDTO created = incidentService.createIncident(incidentDTO);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/{incidentId}")
	public ResponseEntity<IncidentDTO> getIncidentById(@PathVariable int incidentId) {
		IncidentDTO incident = incidentService.getIncidentById(incidentId);
		return ResponseEntity.ok(incident);
	}

	@GetMapping
	public ResponseEntity<List<IncidentDTO>> getAllIncidents() {
		List<IncidentDTO> incidents = incidentService.getAllIncidents();
		return ResponseEntity.ok(incidents);
	}

	@PutMapping("/{incidentId}")
	public ResponseEntity<IncidentDTO> updateIncident(@PathVariable int incidentId, @RequestBody IncidentDTO incidentDTO) {
		IncidentDTO updated = incidentService.updateIncident(incidentId, incidentDTO);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{incidentId}")
	public ResponseEntity<String> deleteIncident(@PathVariable int incidentId) {
		incidentService.deleteIncident(incidentId);
		return ResponseEntity.ok("Incident deleted successfully");
	}
}
