package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.User;
import com.company.Incident.payload.UserDTO;
import com.company.Incident.repository.UserRepository;
import com.company.Incident.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(int userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setUserFname(userDTO.getUserFname());
        user.setUserLname(userDTO.getUserLname());
        user.setDepartment(userDTO.getDepartment());
        user.setUrole(userDTO.getUrole());
        if (userDTO.getBirthDate() != null) {
            user.setBirthDate(LocalDate.parse(userDTO.getBirthDate()));
        }
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setModifiedDate(LocalDate.now());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserFname(userDTO.getUserFname());
        user.setUserLname(userDTO.getUserLname());
        user.setDepartment(userDTO.getDepartment());
        user.setUrole(userDTO.getUrole());
        if (userDTO.getBirthDate() != null) {
            user.setBirthDate(LocalDate.parse(userDTO.getBirthDate()));
        }
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        user.setCreatedBy(userDTO.getCreatedBy());
        user.setCreatedDate(LocalDate.now());
        return user;
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserFname(user.getUserFname());
        userDTO.setUserLname(user.getUserLname());
        userDTO.setDepartment(user.getDepartment());
        userDTO.setUrole(user.getUrole());
        if (user.getBirthDate() != null) {
            userDTO.setBirthDate(user.getBirthDate().toString());
        }
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        userDTO.setCreatedBy(user.getCreatedBy());
        if (user.getCreatedDate() != null) {
            userDTO.setCreatedDate(user.getCreatedDate().toString());
        }
        return userDTO;
    }
}
