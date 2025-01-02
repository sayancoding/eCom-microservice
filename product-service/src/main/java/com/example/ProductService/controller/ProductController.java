package com.example.ProductService.controller;

import com.example.ProductService.dto.ProductRequest;
import com.example.ProductService.dto.ProductResponse;
import com.example.ProductService.exception.NotFoundException;
import com.example.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() throws NotFoundException {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }
}
