package com.company.Incident.payload;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class IncidentDTO {

	@Digits(integer = 10, fraction = 0, message = "Incident ID must be a valid number")
	private int incidentId;

	@Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
	@NotNull(message = "Title is required")
	private String title;

	@Pattern(regexp = "^[a-zA-Z0-9 .,!?-]+$", message = "Description can only contain letters, numbers, spaces, and basic punctuation")
	@NotNull(message = "Description is required")
	private String description;

	private String createdBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String createdDate;

	private String modifiedBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String modifiedDate;

	@NotBlank(message = "Status is required")
	private String status;

	@NotBlank(message = "Priority is required")
	private String priority;

	private String assignmentGroup;
	@NotBlank(message = "SLA date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String slaDate;
	@NotNull(message = "Assigned to is required")
	private int assignedToId;
	@NotBlank(message = "Assigned to is required")
	private String assignedTo;

}
