package com.example.authService.service;

import com.example.authService.dao.UserDao;
import com.example.authService.entity.CustomUserDetails;
import com.example.authService.entity.User;
import com.example.authService.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmailId(username).orElseThrow(() -> new NoUserException("No user found Username with " + username));
        return new CustomUserDetails(user);
    }
}
