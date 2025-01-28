package com.example.controller;

import com.example.client.AuthClient;
import com.example.dto.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gateway")
public class GatewayController {
    @Autowired
    private AuthClient authClient;

    @GetMapping("/validate")
    public Mono<TokenValidationResponse> validateToken(@RequestParam String token){
        return authClient.validateToken(token);
    }
}
