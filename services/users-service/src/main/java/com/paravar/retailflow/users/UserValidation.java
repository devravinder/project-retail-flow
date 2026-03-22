package com.paravar.retailflow.users;

import com.paravar.retailflow.exception.EmailAlreadyExists;
import com.paravar.retailflow.users.persistence.UserEntity;
import com.paravar.retailflow.users.persistence.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class UserValidation {

    private final UserRepo userRepo;

    void validateCreation(UserEntity entity) {

        userRepo.findByEmailHash(entity.getEmailHash())
                .map((user) -> {
                    throw EmailAlreadyExists.of(entity.getEmail());
                });


    }


}
