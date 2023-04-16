package com.demo.service;

import com.demo.dto.CreateUserRequest;
import com.demo.entity.User;
import com.demo.exception.InvalidCredentialException;
import com.demo.exception.ServiceException;
import com.demo.exception.UsernameAlreadyExistException;
import com.demo.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.demo.repositories.UserRepository;
import com.demo.util.BcryptUtil;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User authenticateUser(String username, String password) throws UsernameNotFoundException {
        com.demo.entity.User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else if(!BcryptUtil.verifyHash(password, user.getPassword())){
            throw new InvalidCredentialException("Invalid Credential Provided, Please provide valid username password");
        }
        return user;
    }

    @Override
    public String createUser(CreateUserRequest createUserRequest) throws UsernameAlreadyExistException, ServiceException {
        User user = User.builder()
                .userName(createUserRequest.getUserName())
                .password(BcryptUtil.hashPassword(createUserRequest.getPassword()))
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .email(createUserRequest.getEmail())
                .build();
        User createdUser;
        try {
            createdUser = userRepository.save(user);
        } catch (DataAccessException e){
            throw new UsernameAlreadyExistException("Username already Exists.");
        } catch (Exception e){
            throw new ServiceException("Error while creating User in DB.");
        }
        return createdUser.getUserName();
    }
}
