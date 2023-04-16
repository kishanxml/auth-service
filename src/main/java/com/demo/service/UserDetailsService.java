package com.demo.service;

import com.demo.dto.CreateUserRequest;
import com.demo.entity.User;
import com.demo.exception.InvalidCredentialException;
import com.demo.exception.ServiceException;
import com.demo.exception.UsernameAlreadyExistException;
import com.demo.exception.UsernameNotFoundException;


public interface UserDetailsService {
    User authenticateUser(String username, String password) throws UsernameNotFoundException, InvalidCredentialException;
    String createUser(CreateUserRequest user) throws UsernameAlreadyExistException, ServiceException;
}
