package com.springboot.pruebatecnica.Dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotNull(message = "Este campo no puede estar vacio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$", message = "El nombre solo puede contener letras y espacios")
    private String name;
    @NotNull(message = "La descripción es obligatoria")
    private String description;
    @NotNull(message = "El precio es obligatorio")
    @Digits(integer = 10, fraction = 2, message = "Formato de precio no válido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal price;
}
