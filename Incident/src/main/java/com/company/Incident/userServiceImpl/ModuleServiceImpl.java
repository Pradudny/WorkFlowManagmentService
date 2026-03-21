package com.company.Incident.userServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.Module;
import com.company.Incident.payload.ModuleDTO;
import com.company.Incident.repository.ModuleRepository;
import com.company.Incident.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Override
	public List<ModuleDTO> getAllModules() {
		List<Module> modules = moduleRepository.findAll();
		return modules.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private ModuleDTO mapToDTO(Module module) {
		ModuleDTO dto = new ModuleDTO();
		dto.setModuleId(module.getModule_id());
		dto.setModuleName(module.getModuleName());
		dto.setCreatedBy(module.getCreatedBy());
		dto.setCreatedDate(module.getCreatedDate());
		dto.setModifiedBy(module.getModifiedBy());
		dto.setModifiedDate(module.getModifiedDate());
		return dto;
	}
}
