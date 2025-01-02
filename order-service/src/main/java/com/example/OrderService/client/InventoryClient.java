package com.example.OrderService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service",  path = "/api/inventory")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET,path = "/inStock")
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode , @RequestParam int quantity);
}
