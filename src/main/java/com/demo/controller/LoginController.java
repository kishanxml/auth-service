package com.demo.controller;

import com.demo.entity.User;
import com.demo.dto.UserLoginRequest;
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
public class LoginController {

    @Autowired
    private UserDetailsService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        User user = userService.authenticateUser(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        log.info("User logged in: username={}",user.getUserName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}