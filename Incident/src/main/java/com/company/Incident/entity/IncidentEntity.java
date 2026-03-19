package com.company.Incident.entity;

import java.time.LocalDate;

import com.company.Incident.enums.Priority;
import com.company.Incident.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "incidents")
public class IncidentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int incidentId;
	@Column
	private String title;
	@Column
	private String Description;
	@Column
	private String createdBy;
	@Column
	private String createdDate;

	@Column
	private String modifiedBy;
	@Column
	private String modifiedDate;

	@Enumerated(EnumType.STRING)
	@Column
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column
	private Priority priority;
	@Column
	private String AssignmentGroup;
	@Column
	private LocalDate slaDate;
	@ManyToOne
	@JoinColumn(name = "assidned_to")
	private User assidnedTo;

	public int getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getAssignmentGroup() {
		return AssignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		AssignmentGroup = assignmentGroup;
	}

	public LocalDate getSlaDate() {
		return slaDate;
	}

	public void setSlaDate(LocalDate slaDate) {
		this.slaDate = slaDate;
	}

	public User getAssidnedTo() {
		return assidnedTo;
	}

	public void setAssidnedTo(User assidnedTo) {
		this.assidnedTo = assidnedTo;
	}

}
