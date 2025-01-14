package com.example.authService.controller;

import com.example.authService.dto.LoginDto;
import com.example.authService.dto.RegisterDto;
import com.example.authService.dto.TokenValidationResponse;
import com.example.authService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String welcome(){
        return "Welcome to auth service";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        return userService.verifyUser(loginDto);
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestParam String token){
        return new ResponseEntity<>(new TokenValidationResponse(userService.validateToken(token)), HttpStatus.OK);
    }

}
