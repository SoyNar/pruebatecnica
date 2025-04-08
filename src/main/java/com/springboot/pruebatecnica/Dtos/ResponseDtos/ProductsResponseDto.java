package com.springboot.pruebatecnica.Dtos.ResponseDtos;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponseDto {
    private Integer productId;
    private  String name;
    private  String description;
    private double price;
}
