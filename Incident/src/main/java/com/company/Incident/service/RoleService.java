package com.company.Incident.service;

import java.util.List;

import com.company.Incident.payload.AuthorizeFeatureRequest;
import com.company.Incident.payload.RoleDTO;
import com.company.Incident.payload.RoleFeatureDto;

public interface RoleService {

	List<RoleDTO> getRolesByModule(int moduleId);

	RoleFeatureDto getRolesAndFeaturesByModule(int moduleId);

	void authorizeFeatures(AuthorizeFeatureRequest request);
}
