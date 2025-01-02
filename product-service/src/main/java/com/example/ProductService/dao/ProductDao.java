package com.example.ProductService.dao;

import com.example.ProductService.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends MongoRepository<Product,String> {
}
