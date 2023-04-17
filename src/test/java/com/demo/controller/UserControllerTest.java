package com.demo.controller;

import com.demo.dto.CreateUserRequest;
import com.demo.exception.UsernameAlreadyExistException;
import com.demo.service.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService mockUserService;

    @Test
    void testCreateUser() throws Exception {
        when(mockUserService.createUser(any(CreateUserRequest.class))).thenReturn("result");
        final MockHttpServletResponse response = mockMvc.perform(post("/users")
                        .content("{ \"username\":\"testuser\", \"password\":\"Welcome@123\", \"email\": \"abc@abc.com\", \"firstname\":\"firstName\", \"lastname\":\"lastName\" }").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void testCreateUser_UserDetailsServiceThrowsUsernameAlreadyExistException() throws Exception {
        when(mockUserService.createUser(any(CreateUserRequest.class))).thenThrow(UsernameAlreadyExistException.class);
        final MockHttpServletResponse response = mockMvc.perform(post("/users")
                        .content("{ \"username\":\"testuser\", \"password\":\"Welcome@123\", \"email\": \"abc@abc.com\", \"firstname\":\"firstName\", \"lastname\":\"lastName\" }").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
