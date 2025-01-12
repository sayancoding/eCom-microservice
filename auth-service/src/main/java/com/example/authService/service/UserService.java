package com.example.authService.service;

import com.example.authService.dao.UserDao;
import com.example.authService.dto.LoginDto;
import com.example.authService.dto.RegisterDto;
import com.example.authService.entity.User;
import com.example.authService.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

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
    public String verifyUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(loginDto);
        }
        return "Unable to login!!";
    }
    public boolean validateToken(String token){
        return jwtService.isTokenValid(token);
    }

}
