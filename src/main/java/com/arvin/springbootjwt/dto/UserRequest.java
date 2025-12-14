package com.arvin.springbootjwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    private Integer id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String role;
}


