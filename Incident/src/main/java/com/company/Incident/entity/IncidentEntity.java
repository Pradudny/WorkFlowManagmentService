package com.company.Incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="incidents")
public class IncidentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int incidentId;
	@Column
	private String title;
	
	@Column
	private String createdBy;
	@Column
	private String createdDate;
	
	@Column
	private String modifiedBy;
	@Column
	private String modifiedDate;
	@Column
	private String Description;
	@Column
	private String status;
	@Column
	private int priority;
	@Column
	private String AssignmentGroup;

	@ManyToOne
	@JoinColumn(name="assidned_to")
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getAssignmentGroup() {
		return AssignmentGroup;
	}

	public void setAssignmentGroup(String assignmentGroup) {
		AssignmentGroup = assignmentGroup;
	}

	public User getAssidnedTo() {
		return assidnedTo;
	}

	public void setAssidnedTo(User assidnedTo) {
		this.assidnedTo = assidnedTo;
	}
	
	
}

