package com.app.application.exception;

public class ProductsServiceException extends RuntimeException {
    public ProductsServiceException(String message) {
        super(message);
    }
}
