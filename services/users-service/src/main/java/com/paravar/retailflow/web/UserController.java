package com.paravar.retailflow.web;

import com.paravar.retailflow.users.UserEntity;
import com.paravar.retailflow.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping()
    List<UserEntity> getUsers(){
        return service.getUsers();
    }
}
