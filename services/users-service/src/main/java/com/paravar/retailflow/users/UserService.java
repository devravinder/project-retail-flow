package com.paravar.retailflow.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;

    public List<UserEntity> getUsers(){
        return  repo.findAll();
    }
}
