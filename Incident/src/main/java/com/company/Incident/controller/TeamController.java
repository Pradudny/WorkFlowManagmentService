package com.company.Incident.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.TeamDTO;
import com.company.Incident.service.TeamService;

@RestController
@RequestMapping("/api/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@PostMapping("/add-team")
	public ResponseEntity<TeamDTO> addTeam(@RequestBody TeamDTO teamDTO) {
		TeamDTO created = teamService.addTeam(teamDTO);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/team-details/{teamId}")
	public ResponseEntity<TeamDTO> getTeamDetails(@PathVariable int teamId) {
		TeamDTO team = teamService.getTeamDetails(teamId);
		return ResponseEntity.ok(team);
	}

	@GetMapping("/{moduleId}")
	public ResponseEntity<List<TeamDTO>> getTeamsByModule(@PathVariable int moduleId) {
		List<TeamDTO> teams = teamService.getTeamsByModule(moduleId);
		return ResponseEntity.ok(teams);
	}

	@PutMapping("/update/{teamId}")
	public ResponseEntity<TeamDTO> updateTeam(@PathVariable int teamId, @RequestBody TeamDTO teamDTO) {
		TeamDTO updated = teamService.updateTeam(teamId, teamDTO);
		return ResponseEntity.ok(updated);
	}

	@PutMapping("/add-members/{teamId}")
	public ResponseEntity<TeamDTO> addMembers(@PathVariable int teamId, @RequestBody List<Integer> userIds) {
		TeamDTO updated = teamService.addMembers(teamId, userIds);
		return ResponseEntity.ok(updated);
	}

	@PutMapping("/remove-members/{teamId}")
	public ResponseEntity<TeamDTO> removeMembers(@PathVariable int teamId, @RequestBody List<Integer> userIds) {
		TeamDTO updated = teamService.removeMembers(teamId, userIds);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete/{teamId}")
	public ResponseEntity<String> deleteTeam(@PathVariable int teamId) {
		teamService.deleteTeam(teamId);
		return ResponseEntity.ok("Team deleted successfully");
	}
}
