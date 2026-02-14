package com.paravar.retailflow.users;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<@NonNull UserEntity, @NonNull String> {
}
