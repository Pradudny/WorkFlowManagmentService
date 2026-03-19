package com.company.Incident.service;

import java.util.List;

import com.company.Incident.payload.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(int userId);

	List<UserDTO> getAllUsers();

	UserDTO updateUser(int userId, UserDTO userDTO);

	void deleteUser(int userId);
}
