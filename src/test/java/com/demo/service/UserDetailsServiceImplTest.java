package com.demo.service;

import com.demo.dto.CreateUserRequest;
import com.demo.entity.User;
import com.demo.exception.UsernameNotFoundException;
import com.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImplUnderTest;

    @Test
    void testAuthenticateUser() {
        final User expectedResult = new User(0L, "userName", "$2a$10$8t9XI1/3/N/bzGuvJKFGKOnmmsklj/2JHE94ga2OHYOLXPBSjct1K", "firstName", "lastName", "email");
        final User user = new User(0L, "userName", "$2a$10$8t9XI1/3/N/bzGuvJKFGKOnmmsklj/2JHE94ga2OHYOLXPBSjct1K", "firstName", "lastName", "email");
        when(mockUserRepository.findByUserName("username")).thenReturn(user);
        final User result = userDetailsServiceImplUnderTest.authenticateUser("username", "Welcome@123");
        assertThat(result.getUserName()).isEqualTo(expectedResult.getUserName());
    }

    @Test
    void testAuthenticateUser_UserRepositoryReturnsNull() {
        when(mockUserRepository.findByUserName("username")).thenReturn(null);
        assertThatThrownBy(() -> userDetailsServiceImplUnderTest.authenticateUser("username", "password"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void testCreateUser() {
        final CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .userName("userName")
                .password("password")
                .email("email")
                .build();
        final User user = new User(0L, "userName", "password", "firstName", "lastName", "email");
        when(mockUserRepository.save(any())).thenReturn(user);
        final String result = userDetailsServiceImplUnderTest.createUser(createUserRequest);
        assertThat(result).isEqualTo("userName");
    }
}
