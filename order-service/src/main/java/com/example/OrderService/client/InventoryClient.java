package com.example.OrderService.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {

    @GetExchange("/inStock")
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode , @RequestParam int quantity);
}
