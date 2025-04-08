package com.springboot.pruebatecnica.Exceptions.Customs;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
