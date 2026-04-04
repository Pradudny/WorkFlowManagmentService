package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.Incident;
import com.company.Incident.entity.User;
import com.company.Incident.enums.Priority;
import com.company.Incident.enums.Status;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.IncidentCompletedMessage;
import com.company.Incident.payload.IncidentDTO;
import com.company.Incident.repository.IncidentRepository;
import com.company.Incident.repository.UserRepository;
import com.company.Incident.service.IncidentKafkaProducer;
import com.company.Incident.service.IncidentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidentServiceImpl implements IncidentService {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IncidentKafkaProducer incidentKafkaProducer;

	@Override
	public IncidentDTO createIncident(IncidentDTO incidentDTO, String email) {

		log.info("IncidentServiceImpl::createIncident::Creating new incident with title: {}", incidentDTO.getTitle());
		Incident incident = mapToEntity(incidentDTO);
		incident.setStatus(Status.OPEN);

		if (email != null && !email.isBlank()) {
			log.info("IncidentServiceImpl::createIncident::Setting createdBy to email: {}", email);
			incident.setCreatedBy(email);
		} else if (incidentDTO.getCreatedBy() != null && !incidentDTO.getCreatedBy().isBlank()) {
			log.info("IncidentServiceImpl::createIncident::Setting createdBy from DTO: {}", incidentDTO.getCreatedBy());
			incident.setCreatedBy(incidentDTO.getCreatedBy());
		}
		incident.setCreatedDate(LocalDate.now().toString());
		Incident saved = incidentRepository.save(incident);
		log.info("IncidentServiceImpl::createIncident::Created and saved incident: {}");

		return mapToDTO(saved);
	}

	@Override
	public IncidentDTO getIncidentById(int incidentId) {

		log.info("IncidentServiceImpl::getIncidentById::Fetching incident with id: {}", incidentId);
		Incident incident = incidentRepository.findById(incidentId)

				.orElseThrow(() -> new ResourceNotFoundException("Incident not found for this id : " + incidentId));
		log.info("IncidentServiceImpl::getIncidentById::Fetched incident: {}", incident);
		return mapToDTO(incident);
	}

	@Override
	public List<IncidentDTO> getAllIncidents() {
		log.info("IncidentServiceImpl::getAllIncidents::Fetching all incidents");
		List<Incident> incidents = incidentRepository.findAll();

		log.info("IncidentServiceImpl::getAllIncidents::Fetched incidentsz");
		return incidents.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public IncidentDTO updateIncident(int incidentId, IncidentDTO incidentDTO) {
		log.info("IncidentServiceImpl::updateIncident::Updating incident with id: {}", incidentId);

		Incident incident = incidentRepository.findById(incidentId)
				.orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + incidentId));

		Status previousStatus = incident.getStatus();
		incident.setTitle(incidentDTO.getTitle());
		incident.setDescription(incidentDTO.getDescription());
		if (incidentDTO.getStatus() != null && !incidentDTO.getStatus().isBlank()) {
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

		Incident updated = incidentRepository.save(incident);

		if (previousStatus != Status.RESOLVED && updated.getStatus() == Status.RESOLVED) {
			log.info("Incident status changed from {} to RESOLVED for incidentId={}. Publishing Kafka event.",
					previousStatus, incidentId);
			IncidentCompletedMessage completedMessage = new IncidentCompletedMessage(
					updated.getIncidentId(),
					updated.getTitle(),
					updated.getStatus().name(),
					updated.getModifiedBy(),
					updated.getModifiedDate(),
					previousStatus.name());

			incidentKafkaProducer.publishIncidentCompleted(completedMessage);
		} else {
			log.info("Incident update did not trigger Kafka event. previousStatus={}, newStatus={}",
					previousStatus, updated.getStatus());
		}
		return mapToDTO(updated);

	}

	@Override
	public void deleteIncident(int incidentId) {
		Incident incident = incidentRepository.findById(incidentId)
				.orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + incidentId));
		incidentRepository.delete(incident);
		log.info("IncidentServiceImpl::deleteIncident::Deleted incident with id");
	}

	private Incident mapToEntity(IncidentDTO dto) {
		Incident entity = new Incident();
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

	private IncidentDTO mapToDTO(Incident entity) {
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
			dto.setAssignedToId(entity.getAssidnedTo().getUserId());

		}
		return dto;
	}
}
