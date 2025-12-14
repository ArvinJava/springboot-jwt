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
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserRequest userRequest){
        Users userLogin = new Users();
        userLogin.setEmail(userRequest.getEmail());
        userLogin.setPassword(userRequest.getPassword());
        String token = "";
        try{
            token = usersService.login(userLogin);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("JWT失敗: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("取的JWT: "+token);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.status(HttpStatus.OK).body("test");
    }


}
