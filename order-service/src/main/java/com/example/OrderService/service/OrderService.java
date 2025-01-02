package com.example.OrderService.service;

import com.example.OrderService.client.InventoryClient;
import com.example.OrderService.dao.OrderDao;
import com.example.OrderService.dto.OrderRequest;
import com.example.OrderService.dto.OrderResponse;
import com.example.OrderService.exception.NotFoundException;
import com.example.OrderService.exception.NotInStockException;
import com.example.OrderService.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private InventoryClient inventoryClient;

    public String createOrder(OrderRequest orderRequest) throws NotInStockException {
        if(Boolean.FALSE.equals(inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity()).getBody())){
            throw new NotInStockException("Product with SkuCode : "+orderRequest.skuCode()+" is not in stock");
        }

        Order order = Order.builder()
                .orderNumber("ODR_NO_"+Math.abs(new Random().nextInt()))
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .build();

        orderDao.save(order);
        log.info("New Order is created successfully");
        return "New Order is created successfully";
    }

    public List<OrderResponse> findAll() throws NotFoundException {
        List<OrderResponse> orderResponseList = orderDao.findAll().stream().map(
                el->new OrderResponse(el.getId(),el.getOrderNumber(),el.getSkuCode(),el.getPrice(),el.getQuantity())).toList();

        if (orderResponseList.isEmpty()) {
            throw new NotFoundException("Order list is empty");
        }
        return orderResponseList;
    }
    public OrderResponse findById(long id) throws NotFoundException {
        Order order = orderDao.findById(id)
                .orElseThrow(()-> new NotFoundException("No found such Order with id "+id));
        return new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),order.getQuantity());
    }

}
