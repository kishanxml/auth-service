package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {

    @JsonAlias("username")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, max = 30, message = "The length of username must be between 4 and 30 characters.")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 30, message = "The length of password must be between 8 and 30 characters.")
    private String password;

}
