package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.Role;
import com.company.Incident.entity.Team;
import com.company.Incident.entity.User;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.TeamDTO;
import com.company.Incident.repository.RoleRepository;
import com.company.Incident.repository.TeamRepository;
import com.company.Incident.repository.UserRepository;
import com.company.Incident.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public TeamDTO addTeam(TeamDTO teamDTO) {

		log.info("TeamServiceImpl::addTeam::Adding new team.");

		Team team = mapToEntity(teamDTO);
		team.setCreatedDate(LocalDate.now().toString());
		Team saved = teamRepository.save(team);

		log.info("TeamServiceImpl::addTeam::Added and saved team.");
		return mapToDTO(saved);
	}

	@Override
	public TeamDTO getTeamDetails(int teamId) {
		if (teamId <= 0) {
			log.error("TeamServiceImpl::getTeamDetails::Invalid team id: {}", teamId);
			throw new IllegalArgumentException("Team ID must be a positive integer");
		}
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

		log.info("TeamServiceImpl::getTeamDetails::Fetched team details for team id.");
		return mapToDTO(team);
	}

	@Override
	public List<TeamDTO> getTeamsByModule(int moduleId) {
		List<Team> teams = teamRepository.findByModuleId(moduleId);
		return teams.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	@Override
	public TeamDTO updateTeam(int teamId, TeamDTO teamDTO) {

		log.info("TeamServiceImpl::updateTeam::Updating team with id.");
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

		team.settName(teamDTO.gettName());
		team.settDesc(teamDTO.gettDesc());

		if (teamDTO.getRoleId() > 0) {
			Role role = roleRepository.findById(teamDTO.getRoleId())
					.orElseThrow(() -> new RuntimeException("Role not found with id: " + teamDTO.getRoleId()));
			team.setRoleId(role);
		}

		if (teamDTO.getUserIds() != null && !teamDTO.getUserIds().isEmpty()) {
			List<User> users = userRepository.findAllById(teamDTO.getUserIds());
			team.setUsers(users);
		}

		team.setModifiedBy(teamDTO.getModifiedBy());
		team.setModifiedDate(LocalDate.now().toString());

		log.info("TeamServiceImpl::updateTeam::Updated team details, saving changes.");
		Team updated = teamRepository.save(team);
		return mapToDTO(updated);
	}

	@Override
	public TeamDTO addMembers(int teamId, List<Integer> userIds) {
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

		List<User> newUsers = userRepository.findAllById(userIds);
		List<User> existingUsers = team.getUsers();
		if (existingUsers == null) {
			existingUsers = new ArrayList<>();
		}
		for (User user : newUsers) {
			if (!existingUsers.contains(user)) {
				existingUsers.add(user);
			}
		}
		team.setUsers(existingUsers);
		team.setModifiedDate(LocalDate.now().toString());

		Team updated = teamRepository.save(team);

		log.info("TeamServiceImpl::addMembers::Added members to team.");

		return mapToDTO(updated);
	}

	@Override
	public TeamDTO removeMembers(int teamId, List<Integer> userIds) {

		log.info("TeamServiceImpl::removeMembers::Removing members from team.");

		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

		List<User> existingUsers = team.getUsers();
		if (existingUsers != null) {
			existingUsers.removeIf(user -> userIds.contains(user.getUserId()));
			team.setUsers(existingUsers);
		}
		team.setModifiedDate(LocalDate.now().toString());

		Team updated = teamRepository.save(team);
		log.info("TeamServiceImpl::removeMembers::Removed members from team.");
		return mapToDTO(updated);
	}

	@Override
	public void deleteTeam(int teamId) {

		log.info("TeamServiceImpl::deleteTeam::Deleting team with id.");
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
		log.info("TeamServiceImpl::deleteTeam::Found team, proceeding to delete.");
		teamRepository.delete(team);
	}

	private Team mapToEntity(TeamDTO dto) {
		Team team = new Team();
		team.settName(dto.gettName());
		team.settDesc(dto.gettDesc());
		team.setCreatedBy(dto.getCreatedBy());

		if (dto.getRoleId() > 0) {
			Role role = roleRepository.findById(dto.getRoleId())
					.orElseThrow(() -> new RuntimeException("Role not found with id: " + dto.getRoleId()));
			team.setRoleId(role);
		}

		if (dto.getUserIds() != null && !dto.getUserIds().isEmpty()) {
			List<User> users = userRepository.findAllById(dto.getUserIds());
			team.setUsers(users);
		}

		return team;
	}

	private TeamDTO mapToDTO(Team team) {
		TeamDTO dto = new TeamDTO();
		dto.setTeamId(team.getTeamId());
		dto.settName(team.gettName());
		dto.settDesc(team.gettDesc());
		dto.setCreatedBy(team.getCreatedBy());
		dto.setCreatedDate(team.getCreatedDate());
		dto.setModifiedBy(team.getModifiedBy());
		dto.setModifiedDate(team.getModifiedDate());

		if (team.getRoleId() != null) {
			dto.setRoleId(team.getRoleId().getRoleId());
			dto.setRoleName(team.getRoleId().getRoleName());
		}

		if (team.getUsers() != null) {
			List<Integer> userIds = new ArrayList<>();
			List<String> userNames = new ArrayList<>();
			for (User user : team.getUsers()) {
				userIds.add(user.getUserId());
				userNames.add(user.getUserFname() + " " + user.getUserLname());
			}
			dto.setUserIds(userIds);
			dto.setUserNames(userNames);
		}

		return dto;
	}
}
