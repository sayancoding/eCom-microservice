package com.example.inventoryService.controller;

import com.example.inventoryService.dto.InventoryRequest;
import com.example.inventoryService.dto.InventoryResponse;
import com.example.inventoryService.exception.NotFoundException;
import com.example.inventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody InventoryRequest inventoryRequest) {
        return new ResponseEntity<>(inventoryService.createOrder(inventoryRequest), HttpStatus.CREATED);
    }
//    @GetMapping
//    public ResponseEntity<List<InventoryResponse>> findAll() throws NotFoundException {
//        return new ResponseEntity<>(inventoryService.findAll(), HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<InventoryResponse> findById(@RequestParam long id) throws NotFoundException {
        return new ResponseEntity<>(inventoryService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/inStock")
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode , @RequestParam int quantity) throws NotFoundException {
        return new ResponseEntity<>(inventoryService.isInStockBySkuCode(skuCode,quantity), HttpStatus.OK);
    }
}
