package com.company.Incident.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentCompletedMessage {

    private int incidentId;
    private String title;
    private String status;
    private String completedBy;
    private String completedDate;
    private String prevState;
}
