package com.springboot.pruebatecnica.Services.Impl;
import com.springboot.pruebatecnica.Dtos.ProductRequestDto;
import com.springboot.pruebatecnica.Dtos.ResponseDtos.ProductsResponseDto;
import com.springboot.pruebatecnica.Dtos.UpdateProductRequestDto;
import com.springboot.pruebatecnica.Exceptions.Customs.BusinessException;
import com.springboot.pruebatecnica.Exceptions.Customs.ProductAlreadyExistsException;
import com.springboot.pruebatecnica.Exceptions.Customs.ProductoNotFoundException;
import com.springboot.pruebatecnica.Models.Products;
import com.springboot.pruebatecnica.Respository.IProductsRepository;
import com.springboot.pruebatecnica.Services.IServices.IProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductsService {

    private  final IProductsRepository productsRepository;

    @Transactional
    @Override
    public ProductsResponseDto registerProduct(
            ProductRequestDto requestDto) {

        boolean isExists = this.productsRepository.existsByName(requestDto.getName());
        if (isExists) {
            log.warn("Intento de registrar producto duplicado: {}", requestDto.getName());
            throw new ProductAlreadyExistsException("EL nombrede productoya existe");
        }
        if(requestDto.getPrice().compareTo(BigDecimal.ZERO)<=0){
            log.warn("Intento de registrar producto con precio no indicado {}", requestDto.getPrice());
            throw new BusinessException("the price must be greater than zero ");
        }

        Products products = Products.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .build();
        this.productsRepository.save(products);
        log.info("Producto guardado de forma existosa {}", products.getId());

        return mapToProductResponseDto(products);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsResponseDto> getAllProduct() {
        log.info("Recuperando todos los productos...");
        List<Products> allProducts = this.productsRepository.findAll();
        log.info("Total de productos encontrados: {}", allProducts.size());
        return allProducts.stream().map(this::mapToProductResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductsResponseDto getProductById(Integer id)
    {
        Products product = this.productsRepository.findById(id).orElseThrow(()
                -> new ProductoNotFoundException(" product not found"));
        log.info("Recuperando producto {}", product.getId());

        return mapToProductResponseDto(product);
    }
    @Transactional
    @Override
    public ProductsResponseDto updateProduct(UpdateProductRequestDto requestDto, Integer id) {

        Products product = this.productsRepository.findById(id)
                .orElseThrow(()
                -> { log.warn("Producto con ID {} no encontrado", id);
                   return new ProductoNotFoundException("Product not found"); });

        String oldName = product.getName();
        String oldDescription = product.getDescription();
        BigDecimal oldPRice = product.getPrice();

        if(requestDto.getName() != null && !requestDto.getName().trim().isEmpty()){
            product.setName(requestDto.getName());
            log.info("Nombrea actualizado  anterior {} actual {}", oldName, product.getName());
        }
        if(requestDto.getDescription() != null ){
            product.setDescription(requestDto.getDescription());
            log.info("Nombrea actualizado  anterior {} actual {}", oldDescription, product.getDescription());
        }
        if (requestDto.getPrice() != null) {
            if (requestDto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                log.warn("Intento de actualizar precio con valor negativo: {}", requestDto.getPrice());
                throw new IllegalArgumentException("El precio no puede ser negativo");
            }
            product.setPrice(requestDto.getPrice());
            log.debug("Precio actualizado: {} -> {}", oldPRice, requestDto.getPrice());
        }

        this.productsRepository.save(product);
        log.info("Producto con ID: {} actualizado exitosamente", id);
        return mapToProductResponseDto(product);
    }

    @Transactional
    @Override
    public ProductsResponseDto deleteProduct(Integer id) {

        Products product = this.productsRepository.findById(id).orElseThrow(()
                -> {
            log.warn("Producto con ID {} no encontrado", id);
            return new ProductoNotFoundException("Producto not found");
        });

        log.info("producto borrado {}", product.getId());

        ProductsResponseDto responseDto = mapToProductResponseDto(product);

        try{
            this.productsRepository.delete(product);
            log.info("Producto eliminado con éxito - ID: {}, Nombre: {}", product.getId(), product.getName());
            return responseDto;
        }catch (Exception e){
            log.error("Error al eliminar el producto {} {}", product.getId(), e.getMessage());
            throw new RuntimeException("Error to delete product", e);
        }
    }

    private ProductsResponseDto mapToProductResponseDto(Products products) {
        return ProductsResponseDto.builder()
                .name(products.getName())
                .description(products.getDescription())
                .productId(products.getId())
                .price(products.getPrice().doubleValue())
                .build();
    }
}
