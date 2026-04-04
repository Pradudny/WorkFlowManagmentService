package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.ModuleFeature;
import com.company.Incident.entity.Role;
import com.company.Incident.entity.RoleModuleFeatures;
import com.company.Incident.exception.BadRequestExceptionHandler;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.AuthorizeFeatureRequest;
import com.company.Incident.payload.RoleDTO;
import com.company.Incident.payload.RoleFeatureDto;
import com.company.Incident.repository.ModuleFeatureRepository;
import com.company.Incident.repository.RoleModuleFeatureRepository;
import com.company.Incident.repository.RoleRepository;
import com.company.Incident.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModuleFeatureRepository featureModuleRepository;

	@Autowired
	private RoleModuleFeatureRepository roleModuleFeatureRepository;

	@Override
	public List<RoleDTO> getRolesByModule(int moduleId) {

		log.info("RoleServiceImpl::getRolesByModule::Fetching roles for module");
		List<Role> roles = roleRepository.findByModuleId(moduleId);

		if (roles.isEmpty()) {
			log.warn("RoleServiceImpl::getRolesByModule::No roles found for module with id: {}", moduleId);
		}
		log.info("RoleServiceImpl::getRolesByModule::Fetched roles");
		return roles.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public RoleFeatureDto getRolesAndFeaturesByModule(int moduleId) {

		if (moduleId <= 0) {
			log.error("RoleServiceImpl::getRolesAndFeaturesByModule::Invalid module id: {}", moduleId);
			throw new BadRequestExceptionHandler("Module ID must be a positive integer");
		}

		log.info("RoleServiceImpl::getRolesAndFeaturesByModule::Fetching roles and features for module");
		List<Role> roles = roleRepository.findByModuleId(moduleId);
		List<ModuleFeature> featureModules = featureModuleRepository.findByModuleId(moduleId);

		List<RoleFeatureDto.RoleInfo> roleInfos = roles.stream()
				.map(r -> RoleFeatureDto.RoleInfo.builder()
						.id(r.getRoleId())
						.name(r.getRoleName())
						.build())
				.collect(Collectors.toList());

		List<RoleFeatureDto.FeatureInfo> featureInfos = featureModules.stream()
				.map(fm -> RoleFeatureDto.FeatureInfo.builder()
						.id(fm.getFeatureId().getFeatureId())
						.name(fm.getFeatureId().getFeatureName())
						.build())
				.collect(Collectors.toList());

		log.info("RoleServiceImpl::getRolesAndFeaturesByModule::Fetched roles and features");

		return RoleFeatureDto.builder()
				.roles(roleInfos)
				.features(featureInfos)
				.build();
	}

	@Override
	public void authorizeFeatures(AuthorizeFeatureRequest request) {
		Role role = roleRepository.findById(request.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + request.getRoleId()));

		int moduleId = role.getModuleId().getModule_id();
		List<ModuleFeature> featureModules = featureModuleRepository.findByModuleId(moduleId);

		for (AuthorizeFeatureRequest.FeatureAccess fa : request.getFeatures()) {
			Optional<RoleModuleFeatures> existing = roleModuleFeatureRepository
					.findByRoleIdAndFeatureId(request.getRoleId(), fa.getFeatureId());

			if (fa.isEnabled() && existing.isEmpty()) {
				ModuleFeature fm = featureModules.stream()
						.filter(f -> f.getFeatureId().getFeatureId() == fa.getFeatureId())
						.findFirst()
						.orElseThrow(
								() -> new ResourceNotFoundException("Feature not found with id: " + fa.getFeatureId()));

				RoleModuleFeatures rmf = new RoleModuleFeatures();
				rmf.setRoleId(role);
				rmf.setFeatureModuleId(fm);
				rmf.setCreatedDate(LocalDate.now().toString());
				roleModuleFeatureRepository.save(rmf);
			} else if (!fa.isEnabled() && existing.isPresent()) {
				roleModuleFeatureRepository.delete(existing.get());
			}
		}
	}

	private RoleDTO mapToDTO(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setRoleId(role.getRoleId());
		dto.setRoleName(role.getRoleName());
		dto.setRoleDiscription(role.getRoleDiscription());
		return dto;
	}
}
