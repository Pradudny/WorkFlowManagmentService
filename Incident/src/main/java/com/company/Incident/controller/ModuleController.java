package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.ModuleDTO;
import com.company.Incident.service.ModuleService;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;

	@GetMapping
	public ResponseEntity<List<ModuleDTO>> getAllModules() {
		List<ModuleDTO> modules = moduleService.getAllModules();
		return ResponseEntity.ok(modules);
	}
}
