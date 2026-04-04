package com.company.Incident.payload;

import com.company.Incident.entity.ModuleFeature;

import lombok.Data;

@Data
public class AuthorizationRulesDto {

    private int id;

    private String action;

    private String resource;

    private ModuleFeature moduleFeaturesId;

    private boolean active;

}
