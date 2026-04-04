package com.company.Incident.payload;

import java.time.LocalDate;

import com.company.Incident.entity.Incident;
import com.company.Incident.entity.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TaskDto {

    private Integer incidentId;

    private String taskName;

    private String taskDesc;

    private User assignedTo;

    private String status;

    private String createdBy;

    private String modifiedBy;

    private LocalDate createdDate;

    private LocalDate modifiedDate;
}
