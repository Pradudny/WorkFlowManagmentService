package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.FeatureModule;
import com.company.Incident.entity.Role;
import com.company.Incident.entity.RoleModuleFeatures;
import com.company.Incident.payload.AuthorizeFeatureRequest;
import com.company.Incident.payload.RoleDTO;
import com.company.Incident.payload.RoleFeatureDto;
import com.company.Incident.repository.FeatureModuleRepository;
import com.company.Incident.repository.RoleModuleFeatureRepository;
import com.company.Incident.repository.RoleRepository;
import com.company.Incident.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private FeatureModuleRepository featureModuleRepository;

	@Autowired
	private RoleModuleFeatureRepository roleModuleFeatureRepository;

	@Override
	public List<RoleDTO> getRolesByModule(int moduleId) {
		List<Role> roles = roleRepository.findByModuleId(moduleId);
		return roles.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public RoleFeatureDto getRolesAndFeaturesByModule(int moduleId) {
		List<Role> roles = roleRepository.findByModuleId(moduleId);
		List<FeatureModule> featureModules = featureModuleRepository.findByModuleId(moduleId);

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

		return RoleFeatureDto.builder()
				.roles(roleInfos)
				.features(featureInfos)
				.build();
	}

	@Override
	public void authorizeFeatures(AuthorizeFeatureRequest request) {
		Role role = roleRepository.findById(request.getRoleId())
				.orElseThrow(() -> new RuntimeException("Role not found with id: " + request.getRoleId()));

		int moduleId = role.getModuleId().getModule_id();
		List<FeatureModule> featureModules = featureModuleRepository.findByModuleId(moduleId);

		for (AuthorizeFeatureRequest.FeatureAccess fa : request.getFeatures()) {
			Optional<RoleModuleFeatures> existing = roleModuleFeatureRepository
					.findByRoleIdAndFeatureId(request.getRoleId(), fa.getFeatureId());

			if (fa.isEnabled() && existing.isEmpty()) {
				FeatureModule fm = featureModules.stream()
						.filter(f -> f.getFeatureId().getFeatureId() == fa.getFeatureId())
						.findFirst()
						.orElseThrow(() -> new RuntimeException("Feature not found with id: " + fa.getFeatureId()));

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
