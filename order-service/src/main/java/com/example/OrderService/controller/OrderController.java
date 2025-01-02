package com.example.OrderService.controller;

import com.example.OrderService.dto.OrderRequest;
import com.example.OrderService.dto.OrderResponse;
import com.example.OrderService.exception.NotFoundException;
import com.example.OrderService.exception.NotInStockException;
import com.example.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) throws NotInStockException {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() throws NotFoundException {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable long id) throws NotFoundException {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }
}
