package com.company.Incident.service;

import java.util.List;

import com.company.Incident.payload.IncidentDTO;

public interface IncidentService {

	IncidentDTO createIncident(IncidentDTO incidentDTO);

	IncidentDTO getIncidentById(int incidentId);

	List<IncidentDTO> getAllIncidents();

	IncidentDTO updateIncident(int incidentId, IncidentDTO incidentDTO);

	void deleteIncident(int incidentId);
}
