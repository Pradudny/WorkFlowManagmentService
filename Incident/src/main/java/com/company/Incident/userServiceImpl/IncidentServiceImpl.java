package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.IncidentEntity;
import com.company.Incident.entity.User;
import com.company.Incident.enums.Priority;
import com.company.Incident.enums.Status;
import com.company.Incident.payload.IncidentDTO;
import com.company.Incident.repository.IncidentRepository;
import com.company.Incident.repository.UserRepository;
import com.company.Incident.service.IncidentService;

@Service
public class IncidentServiceImpl implements IncidentService {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public IncidentDTO createIncident(IncidentDTO incidentDTO) {
		IncidentEntity incident = mapToEntity(incidentDTO);
		incident.setCreatedDate(LocalDate.now().toString());
		IncidentEntity saved = incidentRepository.save(incident);
		return mapToDTO(saved);
	}

	@Override
	public IncidentDTO getIncidentById(int incidentId) {
		IncidentEntity incident = incidentRepository.findById(incidentId)
				.orElseThrow(() -> new RuntimeException("Incident not found with id: " + incidentId));
		return mapToDTO(incident);
	}

	@Override
	public List<IncidentDTO> getAllIncidents() {
		List<IncidentEntity> incidents = incidentRepository.findAll();
		return incidents.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public IncidentDTO updateIncident(int incidentId, IncidentDTO incidentDTO) {
		IncidentEntity incident = incidentRepository.findById(incidentId)
				.orElseThrow(() -> new RuntimeException("Incident not found with id: " + incidentId));

		incident.setTitle(incidentDTO.getTitle());
		incident.setDescription(incidentDTO.getDescription());
		if (incidentDTO.getStatus() != null) {
			incident.setStatus(Status.valueOf(incidentDTO.getStatus()));
		}
		if (incidentDTO.getPriority() != null) {
			incident.setPriority(Priority.valueOf(incidentDTO.getPriority()));
		}
		incident.setAssignmentGroup(incidentDTO.getAssignmentGroup());
		if (incidentDTO.getSlaDate() != null) {
			incident.setSlaDate(LocalDate.parse(incidentDTO.getSlaDate()));
		}
		if (incidentDTO.getAssignedTo() != null) {
			int userId = Integer.parseInt(incidentDTO.getAssignedTo());
			User user = userRepository.findById(userId).orElse(null);
			incident.setAssidnedTo(user);
		}
		incident.setModifiedBy(incidentDTO.getModifiedBy());
		incident.setModifiedDate(LocalDate.now().toString());

		IncidentEntity updated = incidentRepository.save(incident);
		return mapToDTO(updated);
	}

	@Override
	public void deleteIncident(int incidentId) {
		IncidentEntity incident = incidentRepository.findById(incidentId)
				.orElseThrow(() -> new RuntimeException("Incident not found with id: " + incidentId));
		incidentRepository.delete(incident);
	}

	private IncidentEntity mapToEntity(IncidentDTO dto) {
		IncidentEntity entity = new IncidentEntity();
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setCreatedBy(dto.getCreatedBy());
		if (dto.getStatus() != null) {
			entity.setStatus(Status.valueOf(dto.getStatus()));
		}
		if (dto.getPriority() != null) {
			entity.setPriority(Priority.valueOf(dto.getPriority()));
		}
		entity.setAssignmentGroup(dto.getAssignmentGroup());
		if (dto.getSlaDate() != null) {
			entity.setSlaDate(LocalDate.parse(dto.getSlaDate()));
		}
		if (dto.getAssignedTo() != null) {
			int userId = Integer.parseInt(dto.getAssignedTo());
			User user = userRepository.findById(userId).orElse(null);
			entity.setAssidnedTo(user);
		}
		return entity;
	}

	private IncidentDTO mapToDTO(IncidentEntity entity) {
		IncidentDTO dto = new IncidentDTO();
		dto.setIncidentId(entity.getIncidentId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedBy(entity.getModifiedBy());
		dto.setModifiedDate(entity.getModifiedDate());
		if (entity.getStatus() != null) {
			dto.setStatus(entity.getStatus().name());
		}
		if (entity.getPriority() != null) {
			dto.setPriority(entity.getPriority().name());
		}
		dto.setAssignmentGroup(entity.getAssignmentGroup());
		if (entity.getSlaDate() != null) {
			dto.setSlaDate(entity.getSlaDate().toString());
		}
		if (entity.getAssidnedTo() != null) {
			dto.setAssignedTo(entity.getAssidnedTo().getUserFname() + " " + entity.getAssidnedTo().getUserLname());
		}
		return dto;
	}
}
