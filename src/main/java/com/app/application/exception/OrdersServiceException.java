package com.app.application.exception;

public class OrdersServiceException extends RuntimeException {
    public OrdersServiceException(String message) {
        super(message);
    }
}
