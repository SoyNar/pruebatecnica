package com.springboot.pruebatecnica.Services.IServices;
import com.springboot.pruebatecnica.Dtos.ProductRequestDto;
import com.springboot.pruebatecnica.Dtos.ResponseDtos.ProductsResponseDto;
import com.springboot.pruebatecnica.Dtos.UpdateProductRequestDto;
import java.util.List;

public interface IProductsService {
    ProductsResponseDto registerProduct(
            ProductRequestDto registerProductRequestDto);
    List<ProductsResponseDto> getAllProduct();
    ProductsResponseDto getProductById(Integer id);
    ProductsResponseDto updateProduct(UpdateProductRequestDto requestDto,
                                      Integer id);
    ProductsResponseDto deleteProduct(Integer id);
}
