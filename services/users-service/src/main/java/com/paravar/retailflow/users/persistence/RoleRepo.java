package com.paravar.retailflow.users.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface RoleRepo extends JpaRepository<RoleEntity, String> {
    Set<RoleEntity> findByNameIn(Set<String> names);

}
