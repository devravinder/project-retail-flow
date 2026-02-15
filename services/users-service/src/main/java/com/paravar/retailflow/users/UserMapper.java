package com.paravar.retailflow.users;

import com.paravar.retailflow.users.dto.*;
import com.paravar.retailflow.users.persistence.*;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNames")
    @Mapping(target = "addresses", source = "addresses")
    List<UserDto> toDtoList(List<UserEntity> entities);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleNames")
    @Mapping(target = "addresses", source = "addresses")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "emailHash", ignore = true)
    @Mapping(target = "phoneHash", ignore = true)
    @Mapping(target = "roles", ignore = true)         // roles handled separately
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "active", defaultValue = "true")
    UserEntity toEntity(UserCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "emailHash", ignore = true)
    @Mapping(target = "phoneHash", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(UserUpdateDto dto, @MappingTarget UserEntity entity);

    // Custom mapping for roles → role names
    @Named("roleNames")
    default Set<String> mapRoleNames(Set<RoleEntity> roles) {
        if (roles == null) return Set.of();
        return roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());
    }

    // Address mappings
    AddressResponseDto toAddressResponseDto(AddressEntity address);

    @Mapping(target = "user", ignore = true)
    AddressEntity toAddressEntity(AddressCreateOrUpdateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    void updateAddressFromDto(AddressCreateOrUpdateDto dto, @MappingTarget AddressEntity address);
}