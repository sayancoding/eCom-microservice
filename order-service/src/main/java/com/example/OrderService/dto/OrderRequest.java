package com.example.OrderService.dto;

import lombok.Data;

public record OrderRequest(String orderNumber,String skuCode,long price,int quantity) {
}
