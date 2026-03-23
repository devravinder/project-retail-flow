package com.paravar.retailflow.users.api;

import com.paravar.retailflow.exception.EmailAlreadyExists;
import com.paravar.retailflow.exception.ErrorResponse;
import com.paravar.retailflow.users.UserService;
import com.paravar.retailflow.users.dto.UserCreateDto;
import com.paravar.retailflow.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {

    private final UserService service;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    List<UserDto> getUsers(){
        return service.getUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN') or #id == authentication.name")
    UserDto getUser(@PathVariable String id){
        return service.getUserById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    UserDto createUser(@RequestBody UserCreateDto dto){
        return service.createUser(dto);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmailAlreadyExists ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .validationErrors(Collections.emptyMap())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
