package com.example.authService.service;

import com.example.authService.dao.UserDao;
import com.example.authService.dto.RegisterDto;
import com.example.authService.entity.User;
import com.example.authService.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmailId(String emailId) {
        return userDao.findByEmailId(emailId).orElseThrow(() -> new NoUserException("No user found emailId with " + emailId));
    }
    public String register(RegisterDto registerDto){
        User user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .emailId(registerDto.getEmailId())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(registerDto.getRoles())
                .build();
        userDao.save(user);
        return "new user is created";
    }

}
