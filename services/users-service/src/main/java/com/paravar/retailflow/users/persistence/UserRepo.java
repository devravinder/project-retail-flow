package com.paravar.retailflow.users.persistence;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,  String> {

     Optional<UserEntity> findByEmailHash(String emailHash);
}
