package com.paravar.retailflow.users.api;

import com.paravar.retailflow.users.UserService;
import com.paravar.retailflow.users.dto.UserCreateDto;
import com.paravar.retailflow.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

    private final UserService service;

    @GetMapping()
    List<UserDto> getUsers(){
        return service.getUsers();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable String id){
        return service.getUserById(id);
    }

    @PostMapping()
    UserDto createUser(@RequestBody UserCreateDto dto){
        return service.createUser(dto);
    }
}
