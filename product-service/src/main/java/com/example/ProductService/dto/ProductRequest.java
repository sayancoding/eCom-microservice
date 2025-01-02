package com.example.ProductService.dto;

public record ProductRequest
        (String name,String description,String category,long price) { }
