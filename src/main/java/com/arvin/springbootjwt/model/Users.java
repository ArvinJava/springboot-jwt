package com.arvin.springbootjwt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", unique = true)
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;
}
