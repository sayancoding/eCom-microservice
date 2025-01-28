package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallBackController {
    @RequestMapping("/auth")
    public Mono<ResponseEntity<?>> fallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Now Auth-Service is not available, Try again later !!"));
    }
    @RequestMapping("/product")
    public Mono<ResponseEntity<?>> productFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Now Product-Service is not available, Try again later !!"));
    }
    @RequestMapping("/inventory")
    public Mono<ResponseEntity<?>> inventoryFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Now Inventory-Service is not available, Try again later !!"));
    }
    @RequestMapping("/order")
    public Mono<ResponseEntity<?>> orderFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Now Order-Service is not available, Try again later !!"));
    }
}
