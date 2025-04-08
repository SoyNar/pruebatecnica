package com.springboot.pruebatecnica.Exceptions.Customs;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
