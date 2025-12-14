package com.arvin.springbootjwt.controller;

import com.arvin.springbootjwt.dto.UserRequest;
import com.arvin.springbootjwt.model.Users;
import com.arvin.springbootjwt.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/registe")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest){
        String email = userRequest.getEmail();
        Users user = usersService.findUserByEmail(email);
        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("該email已註冊");
        }

        Users addUsers = new Users();
        addUsers.setEmail(userRequest.getEmail());
        addUsers.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        addUsers.setRole("ADMIN");
        Users newUser = usersService.addUser(addUsers);
        return ResponseEntity.status(HttpStatus.OK).body("註冊成功");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> {
                    formLogin.disable();
                })
                .authorizeHttpRequests(auth -> auth
                        //permitAll() 必須在authenticated()後，否則無效
                        .requestMatchers("/registe").permitAll() // 首頁不需要登入
                        .anyRequest().authenticated()// 其他需要登入

                );
        return http.build();
    }

}
