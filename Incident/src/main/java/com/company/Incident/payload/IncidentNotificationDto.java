package com.company.Incident.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentNotificationDto {

	private int incidentId;
	private String incidentTitle;
	private String status;
	private String message;
}
