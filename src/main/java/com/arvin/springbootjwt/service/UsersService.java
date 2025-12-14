package com.arvin.springbootjwt.service;

import com.arvin.springbootjwt.model.Users;

public interface UsersService {

    Users findUserByEmail(String email);

    Users addUser(Users user);
}
