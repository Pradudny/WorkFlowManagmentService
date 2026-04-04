package com.company.Incident.entity;

import java.time.LocalDate;

import com.company.Incident.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "task")
@Data
@RequiredArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_id")
    @JsonIgnore
    private Incident incident;

    @Column
    private String taskName;

    @Column
    private String taskDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    @JsonIgnore
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Column
    private String createdBy;
    @Column
    private String modifiedBy;
    @Column
    private LocalDate createdDate;
    @Column
    private LocalDate modifiedDate;
}
