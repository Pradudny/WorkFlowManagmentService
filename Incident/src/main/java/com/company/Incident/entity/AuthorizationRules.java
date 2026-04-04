package com.company.Incident.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "authorization_rules")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthorizationRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String action;

    @Column
    private String resource;

    @ManyToOne
    @JoinColumn(name = "module_features_id")
    private ModuleFeature moduleFeaturesId;

    @Column
    private boolean active;
}
