package com.example.ProductService.service;

import com.example.ProductService.dao.ProductDao;
import com.example.ProductService.dto.ProductRequest;
import com.example.ProductService.dto.ProductResponse;
import com.example.ProductService.exception.NotFoundException;
import com.example.ProductService.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public String createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .category(productRequest.category())
                .price(productRequest.price())
                .build();

        productDao.save(product);
        log.info("New Product is created successfully");
        return "New Product is created successfully";
    }

    public List<ProductResponse> findAll() throws NotFoundException {
        List<ProductResponse> productResponseList = productDao.findAll().stream().map(
                el->new ProductResponse(el.getId(),el.getName(),el.getDescription(),el.getCategory(),el.getPrice())).toList();

        if (productResponseList.isEmpty()) {
            throw new NotFoundException("Product list is empty");
        }
        return productResponseList;
    }
    public ProductResponse findById(String id) throws NotFoundException {
        Product product = productDao.findById(id)
                .orElseThrow(()-> new NotFoundException("No found such product with id "+id));
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getCategory(),product.getPrice());
    }

}
