package com.springboot.pruebatecnica.Dtos;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
}
