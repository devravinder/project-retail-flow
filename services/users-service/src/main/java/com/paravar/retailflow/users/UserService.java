package com.paravar.retailflow.users;

import com.paravar.retailflow.exception.EntityNotFoundException;
import com.paravar.retailflow.users.dto.UserCreateDto;
import com.paravar.retailflow.users.dto.UserDto;
import com.paravar.retailflow.users.dto.UserUpdateDto;
import com.paravar.retailflow.users.persistence.RoleEntity;
import com.paravar.retailflow.users.persistence.RoleRepo;
import com.paravar.retailflow.users.persistence.UserEntity;
import com.paravar.retailflow.users.persistence.UserRepo;
import com.paravar.retailflow.util.HashingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepository;
    private final UserMapper userMapper;
    private  final HashingUtil hashUtil;
    private final RoleRepo roleRepo;

    public List<UserDto> getUsers(){
        return  userMapper.toDtoList(userRepository.findAll());
    }

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        UserEntity entity = userMapper.toEntity(dto);

        // Hash  separately
        entity.setEmailHash(hashUtil.blindIndex(dto.getEmail()));
        entity.setPhoneHash(hashUtil.blindIndex(dto.getPhone()));

        // Handle roles (example)
        Set<RoleEntity> roles = roleRepo.findByNameIn(dto.getRoleNames());
        entity.setRoles(roles);

        UserEntity saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(String id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return userMapper.toDto(entity);
    }

    @Transactional
    public UserDto updateUser(String id, UserUpdateDto dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

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
