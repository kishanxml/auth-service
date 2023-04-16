package com.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserRequest {

    @JsonAlias("firstname")
    @NotBlank(message = "FirstName is mandatory")
    @Size(min = 2, max = 30, message = "The length of FirstName must be between 2 and 30 characters.")
    @Pattern(regexp = "[A-Za-z]+",message="FirstName only allows letters.")
    private String firstName;

    @JsonAlias("lastname")
    @NotBlank(message = "LastName is mandatory")
    @Size(min = 2, max = 30, message = "The length of LastName must be between 2 and 30 characters.")
    @Pattern(regexp = "[A-Za-z]+" ,message="LastName only allows letters")
    private String lastName;

    @JsonAlias("username")
    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 30, message = "username only allows letters, number, and some special characters(._@).")
    @Pattern(regexp = "[A-Za-z0-9@_.]+")
    private String userName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 30, message = "The length of password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",message="Password must contain once special character, once uppercase, once lower case and one number.")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Size(min = 2, max = 30, message = "The length of email must be between 2 and 30 characters.")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

}
