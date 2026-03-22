package com.paravar.retailflow.users;

import com.paravar.retailflow.ApplicationProperties;
import com.paravar.retailflow.exception.AuthServerException;
import com.paravar.retailflow.exception.EntityNotFoundException;
import com.paravar.retailflow.users.dto.UserCreateDto;
import com.paravar.retailflow.users.dto.UserDto;
import com.paravar.retailflow.users.dto.UserUpdateDto;
import com.paravar.retailflow.users.persistence.RoleEntity;
import com.paravar.retailflow.users.persistence.RoleRepo;
import com.paravar.retailflow.users.persistence.UserEntity;
import com.paravar.retailflow.users.persistence.UserRepo;
import com.paravar.retailflow.util.HashingUtil;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private  final HashingUtil hashUtil;
    private final RoleRepo roleRepo;
    private final Keycloak keycloakAdmin;
    private final ApplicationProperties properties;
    private final UserValidation validation;

    public List<UserDto> getUsers(){
        return  userMapper.toDtoList(userRepository.findAll());
    }

    @Transactional
    public UserDto createUser(UserCreateDto dto) {

        UserEntity entity = userMapper.toEntity(dto);

        // Hash  separately
        entity.setEmailHash(hashUtil.blindIndex(dto.email()));
        entity.setPhoneHash(hashUtil.blindIndex(dto.phone()));

        // Handle roles (example)
        Set<RoleEntity> roles = roleRepo.findByNameIn(dto.roleNames());
        entity.setRoles(roles);

        validation.validateCreation(entity);

        String keycloakUserId = createKeycloakUser(dto);

        entity.setId(keycloakUserId);
        UserEntity saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    private String createKeycloakUser(UserCreateDto dto) {

        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(dto.email());
        userRep.setEmail(dto.email());
        userRep.setFirstName(dto.firstName());
        userRep.setLastName(dto.lastName());
        userRep.setEnabled(true);
        userRep.setRealmRoles(dto.roleNames().stream().toList()); // not working

        // Phone as Keycloak attribute (optional)
        Map<String, List<String>> attrs = new HashMap<>();
        attrs.put("phone", Collections.singletonList(dto.phone()));
        userRep.setAttributes(attrs);

        // Password
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(dto.password());
        cred.setTemporary(false);
        userRep.setCredentials(Collections.singletonList(cred));

        UsersResource usersResource = keycloakAdmin.realm(properties.keycloak().realm()).users();

        try (Response response = usersResource.create(userRep)) {
            if (response.getStatus() == 201) {
                // Extract Keycloak user ID from Location header
                String location = response.getLocation().toString();
                return location.substring(location.lastIndexOf("/") + 1);
            } else {
                throw AuthServerException.of(response.getStatus(), response.getEntity().toString());
            }
        }
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(String id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.of("User not found"));
        return userMapper.toDto(entity);
    }

    @Transactional
    public UserDto updateUser(String id, UserUpdateDto dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.of("User not found"));

        userMapper.updateFromDto(dto, entity);

        // Handle role updates if present
        if (dto.getRoleNames() != null && !dto.getRoleNames().isEmpty()) {
            Set<RoleEntity> roles = roleRepo.findByNameIn(dto.getRoleNames());
            entity.setRoles(roles);
        }

        UserEntity updated = userRepository.save(entity);
        return userMapper.toDto(updated);
    }
}
