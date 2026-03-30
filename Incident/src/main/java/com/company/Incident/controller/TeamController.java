package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.TeamDTO;
import com.company.Incident.service.TeamService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@PostMapping("/add-team")
	public ResponseEntity<TeamDTO> addTeam(@RequestBody @Valid TeamDTO teamDTO) {
		TeamDTO created = teamService.addTeam(teamDTO);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/team-details")
	public ResponseEntity<TeamDTO> getTeamDetails(
			@RequestParam(value = "teamId") @Min(value = 1, message = "Team ID must be a positive integer") int teamId) {
		TeamDTO team = teamService.getTeamDetails(teamId);
		return ResponseEntity.ok(team);
	}

	@GetMapping("/teams-by-module")
	public ResponseEntity<List<TeamDTO>> getTeamsByModule(
			@RequestParam(value = "moduleId") @Min(value = 1, message = "Module ID must be a positive integer") int moduleId) {
		List<TeamDTO> teams = teamService.getTeamsByModule(moduleId);
		return ResponseEntity.ok(teams);
	}

	@PutMapping("/update-team")
	public ResponseEntity<TeamDTO> updateTeam(
			@RequestParam(value = "teamId") @Min(value = 1, message = "Team ID must be a positive integer") int teamId,
			@RequestBody @Valid TeamDTO teamDTO) {
		TeamDTO updated = teamService.updateTeam(teamId, teamDTO);
		return ResponseEntity.ok(updated);
	}

	@PutMapping("/add-team-members")
	public ResponseEntity<TeamDTO> addMembers(
			@RequestParam(value = "teamId") @Min(value = 1, message = "Team ID must be a positive integer") int teamId,
			@RequestBody @Valid List<Integer> userIds) {
		TeamDTO updated = teamService.addMembers(teamId, userIds);
		return ResponseEntity.ok(updated);
	}

	@PutMapping("/remove-team-members")
	public ResponseEntity<TeamDTO> removeMembers(
			@RequestParam(value = "teamId") @Min(value = 1, message = "Team ID must be a positive integer") int teamId,
			@RequestBody @Valid List<Integer> userIds) {
		TeamDTO updated = teamService.removeMembers(teamId, userIds);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete-team")
	public ResponseEntity<String> deleteTeam(
			@RequestParam(value = "teamId") @Min(value = 1, message = "Team ID must be a positive integer") int teamId) {
		teamService.deleteTeam(teamId);
		return ResponseEntity.ok("Team deleted successfully");
	}
}
