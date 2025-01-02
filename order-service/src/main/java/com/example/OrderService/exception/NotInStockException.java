package com.example.OrderService.exception;

public class NotInStockException extends Exception{
    public NotInStockException(String msg){
        super(msg);
    }
}
