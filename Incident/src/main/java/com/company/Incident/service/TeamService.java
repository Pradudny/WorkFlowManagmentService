package com.company.Incident.service;

import java.util.List;

import com.company.Incident.payload.TeamDTO;

public interface TeamService {

	TeamDTO addTeam(TeamDTO teamDTO);

	TeamDTO getTeamDetails(int teamId);

	List<TeamDTO> getTeamsByModule(int moduleId);

	TeamDTO updateTeam(int teamId, TeamDTO teamDTO);

	void deleteTeam(int teamId);

	TeamDTO addMembers(int teamId, List<Integer> userIds);

	TeamDTO removeMembers(int teamId, List<Integer> userIds);
}
