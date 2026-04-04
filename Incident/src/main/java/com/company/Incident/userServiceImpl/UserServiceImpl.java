package com.company.Incident.userServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.Incident.entity.AuthorizationRules;
import com.company.Incident.entity.ModuleFeature;
import com.company.Incident.entity.User;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.UserDTO;
import com.company.Incident.repository.AuthorizationRulesRepo;
import com.company.Incident.repository.ModuleFeatureRepository;
import com.company.Incident.repository.UserRepository;
import com.company.Incident.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationRulesRepo authorizationRulesRepo;

    @Autowired
    private ModuleFeatureRepository moduleFeatureRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        log.info("UserServiceImpl::createUser::Creating new user.");
        User user = mapToEntity(userDTO);
        User savedUser = userRepository.save(user);
        log.info("UserServiceImpl::createUser::User created and saved.");
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(int userId) {

        log.info("UserServiceImpl::getUserById::Fetching user with id.");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        log.info("UserServiceImpl::getUserById::Fetched user with id.");
        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("UserServiceImpl::getAllUsers::Fetching all users.");
        List<User> users = userRepository.findAll();
        log.info("UserServiceImpl::getAllUsers::Fetched all users.");
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(int userId, UserDTO userDTO) {
        log.info("UserServiceImpl::updateUser::Updating user with id.");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setUserFname(userDTO.getUserFname());
        user.setUserLname(userDTO.getUserLname());
        user.setDepartment(userDTO.getDepartment());
        if (userDTO.getBirthDate() != null) {
            user.setBirthDate(LocalDate.parse(userDTO.getBirthDate()));
        }
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setModifiedDate(LocalDate.now());

        User updatedUser = userRepository.save(user);

        log.info("UserServiceImpl::updateUser::Updated and saved user.");

        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(int userId) {
        log.info("UserServiceImpl::deleteUser::Deleting user with id.");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
        log.info("UserServiceImpl::deleteUser::Deleted user.");
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserFname(userDTO.getUserFname());
        user.setUserLname(userDTO.getUserLname());
        user.setDepartment(userDTO.getDepartment());
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
        userDTO.setUserId(user.getUserId());
        userDTO.setUserFname(user.getUserFname());
        userDTO.setUserLname(user.getUserLname());
        userDTO.setDepartment(user.getDepartment());
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

    // public String authorizeUser(String action, String resource, String
    // authorizationHeader, String email) {
    // log.info(
    // "UserServiceImpl::authorizeUser::Authorizing user for action: {}, resource:
    // {}, email: {}",
    // action, resource, email);

    // List<AuthorizationRules> authorizeResource =
    // authorizationRulesRepo.findByActionAndResource(action, resource);
    // List<ModuleFeature> allowedModuleFeatureIdList = authorizeResource.stream()
    // .map(AuthorizationRules::getModuleFeaturesId).collect(Collectors.toList());

    // List<ModuleFeature> userModuleFeatures =
    // moduleFeatureRepository.findByUserEmailId(email);

    // boolean isAuthorized = userModuleFeatures.stream()
    // .anyMatch(userModuleFeature -> allowedModuleFeatureIdList.stream()
    // .anyMatch(allowedFeature ->
    // allowedFeature.getModuleFeaturesId().equals(userModuleFeature.getModuleFeaturesId())));

    // if (isAuthorized) {
    // return email;
    // }

    // return null;
    // }

    private String normalizeResource(String resource) {
        return resource.replaceAll("/\\d+(?=/|$)", "/{incidentId}");
    }

    private String convertToRegex(String pattern) {
        return pattern
                .replaceAll("\\{[^/]+\\}", "[^/]+"); // {id} → match anything except '/'
    }

    public String authorizeUser(String action, String resource, String authorizationHeader, String email) {

        log.info(
                "UserServiceImpl::authorizeUser::Authorizing user for action: {}, resource: {}, email: {}",
                action, resource, email);

        try {
            // System.out.println("resource before normalization: " + resource);
            // String nResponse = normalizeResource(resource);
            // System.out.println("resource after normalization: " + nResponse);

            // Step 1: Get allowed features for given action + resource
            log.info("Starting authorization process for action: {}, resource: {}", action, resource);
            List<AuthorizationRules> authorizeResource = authorizationRulesRepo.findByActionAndResource(action,
                    resource);

            log.info("Completed fetching authorization rules. Number of rules found: {}", authorizeResource.size());

            Set<ModuleFeature> allowedFeatures = authorizeResource.stream()
                    .map(rule -> rule.getModuleFeaturesId())
                    .collect(Collectors.toSet());

            log.info("Extracted allowed module features. Number of unique features: {}", allowedFeatures.size());
            log.info("Allowed module features from authrules table: {}",
                    allowedFeatures.stream().map(mf -> mf.getModuleFeaturesId()).collect(Collectors.toList()));

            // Step 2: Get user's module features
            List<ModuleFeature> userModuleFeatures = moduleFeatureRepository.findByUserEmailId(email);

            log.info("Retrieved user module features for email: {}", email);
            log.info("User module features from RMF : {}",
                    userModuleFeatures.stream().map(mf -> mf.getModuleFeaturesId()).collect(Collectors.toList()));

            // Step 3: Check authorization (optimized using Set)
            boolean isAuthorized = userModuleFeatures.stream()
                    .anyMatch(feature -> allowedFeatures.contains(feature));

            // Step 4: Return result
            if (isAuthorized) {
                return email;
            }
        } catch (Exception ex) {
            log.error("UserServiceImpl::authorizeUser::Error during authorization: {}", ex.getMessage());
            throw new RuntimeException("Error during authorization");
        }
        throw new RuntimeException("User not authorized for this action and resource");
    }
}
