package com.springboot.pruebatecnica.Exceptions.Dtos;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private String message;
    private int code;
    private String error;
    private LocalDate date;
}
