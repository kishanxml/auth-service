package com.demo.controller;

import com.demo.dto.CreateUserRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.demo.service.UserDetailsService;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserDetailsService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUser) {
        String createdUser = userService.createUser(createUser);
        log.info("User created: username={}",createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}