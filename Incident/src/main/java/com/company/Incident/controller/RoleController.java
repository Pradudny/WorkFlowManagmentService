package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.AuthorizeFeatureRequest;
import com.company.Incident.payload.RoleDTO;
import com.company.Incident.payload.RoleFeatureDto;
import com.company.Incident.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/{moduleId}")
	public ResponseEntity<List<RoleDTO>> getRolesByModule(@PathVariable int moduleId) {
		List<RoleDTO> roles = roleService.getRolesByModule(moduleId);
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/roles-features/{moduleId}")
	public ResponseEntity<RoleFeatureDto> getRolesAndFeaturesByModule(@PathVariable int moduleId) {
		RoleFeatureDto dto = roleService.getRolesAndFeaturesByModule(moduleId);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/authorize-feature")
	public ResponseEntity<String> authorizeFeatures(@RequestBody AuthorizeFeatureRequest request) {
		roleService.authorizeFeatures(request);
		return ResponseEntity.ok("Feature authorization updated successfully");
	}
}
