package com.arvin.springbootjwt.service.Impl;

import com.arvin.springbootjwt.model.Users;
import com.arvin.springbootjwt.repository.UserRepository;
import com.arvin.springbootjwt.service.UsersService;
import com.arvin.springbootjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users addUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public String login(Users user) throws Exception {
        Users u = this.findUserByEmail(user.getEmail());
        if(!passwordEncoder.matches(user.getPassword(), u.getPassword())){
            throw new Exception("登入失敗");
        }

        long expire = 1000 *60 *10;
        String subject = user.getEmail();
        String jwt = JwtUtil.createJWT(subject, expire);
        return jwt;
    }

}
