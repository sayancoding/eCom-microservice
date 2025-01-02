package com.example.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "product")
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private long price;
}
