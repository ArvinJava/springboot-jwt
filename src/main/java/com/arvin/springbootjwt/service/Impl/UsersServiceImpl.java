package com.arvin.springbootjwt.service.Impl;

import com.arvin.springbootjwt.model.Users;
import com.arvin.springbootjwt.repository.UserRepository;
import com.arvin.springbootjwt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users addUser(Users user) {
        return userRepository.save(user);
    }
}
