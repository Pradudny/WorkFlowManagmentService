package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.AuthorizeFeatureRequest;
import com.company.Incident.payload.RoleDTO;
import com.company.Incident.payload.RoleFeatureDto;
import com.company.Incident.service.RoleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("/{moduleId}")
	public ResponseEntity<List<RoleDTO>> getRolesByModule(
			@PathVariable @Min(value = 1, message = "Module ID must be a positive integer") int moduleId) {
		List<RoleDTO> roles = roleService.getRolesByModule(moduleId);
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/roles-features")
	public ResponseEntity<RoleFeatureDto> getRolesAndFeaturesByModule(
			@RequestParam(value = "moduleId") @Min(value = 1, message = "Module ID must be a positive integer") int moduleId) {
		RoleFeatureDto dto = roleService.getRolesAndFeaturesByModule(moduleId);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/authorize-feature")
	public ResponseEntity<String> authorizeFeatures(@RequestBody @Valid AuthorizeFeatureRequest request) {
		roleService.authorizeFeatures(request);
		return ResponseEntity.ok("Feature authorization updated successfully");
	}
}
