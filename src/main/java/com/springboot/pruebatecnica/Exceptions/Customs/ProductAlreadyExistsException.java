package com.springboot.pruebatecnica.Exceptions.Customs;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
