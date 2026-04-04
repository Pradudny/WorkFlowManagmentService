package com.company.Incident.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Length;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.Incident.payload.UserDTO;
import com.company.Incident.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@Slf4j
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add-user")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
		UserDTO createdUser = userService.createUser(userDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/user-details")
	public ResponseEntity<UserDTO> getUserById(
			@RequestParam(value = "userId") @Min(value = 1, message = "User ID must be a positive integer") int userId) {
		UserDTO userDTO = userService.getUserById(userId);
		return ResponseEntity.ok(userDTO);
	}

	@GetMapping("/users-list")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@PutMapping("/update-user")
	public ResponseEntity<UserDTO> updateUser(
			@RequestParam(value = "userId") @Min(value = 1, message = "User ID must be a positive integer") int userId,
			@RequestBody @Valid UserDTO userDTO) {
		UserDTO updatedUser = userService.updateUser(userId, userDTO);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/delete-user")
	public ResponseEntity<String> deleteUser(
			@RequestParam(value = "userId") @Min(value = 1, message = "User ID must be a positive integer") int userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok("User deleted successfully");
	}

	@PostMapping("/authorize-user")
	public ResponseEntity<String> authorizeUser(
			@RequestParam String action,
			@RequestParam String resource,
			@RequestHeader("Authorization") String authorizationHeader,
			@RequestHeader("X-User-Email") String email) {

		try {
			String result = userService.authorizeUser(action, resource, authorizationHeader, email);
			return ResponseEntity.ok(result);
		} catch (RuntimeException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized");
		}
	}

}
