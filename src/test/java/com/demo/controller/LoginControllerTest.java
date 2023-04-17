package com.demo.controller;

import com.demo.entity.User;
import com.demo.exception.InvalidCredentialException;
import com.demo.exception.UsernameNotFoundException;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService mockUserService;

    @Test
    void testAuthenticateUser() throws Exception {
        final User user = new User(0L, "userName", "Welcome@123", "firstName", "lastName", "email@email.com");
        when(mockUserService.authenticateUser("userName", "Welcome@123")).thenReturn(user);
       final MockHttpServletResponse response = mockMvc.perform(post("/authenticate")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                       .content("{\"username\":\"userName\",\"password\":\"Welcome@123\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testAuthenticateUser_UserDetailsServiceThrowsUsernameNotFoundException() throws Exception {
        when(mockUserService.authenticateUser("userName", "Welcome@123")).thenThrow(UsernameNotFoundException.class);
        final MockHttpServletResponse response = mockMvc.perform(post("/authenticate")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"userName\",\"password\":\"Welcome@123\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

}
