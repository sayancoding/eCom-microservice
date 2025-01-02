package com.example.OrderService.dto;

public record OrderResponse(long id,String orderNumber,String skuCode,long price,int quantity) {
}
