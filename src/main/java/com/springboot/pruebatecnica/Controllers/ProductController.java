package com.springboot.pruebatecnica.Controllers;

import com.springboot.pruebatecnica.Dtos.ProductRequestDto;
import com.springboot.pruebatecnica.Dtos.ResponseDtos.ProductsResponseDto;
import com.springboot.pruebatecnica.Dtos.UpdateProductRequestDto;
import com.springboot.pruebatecnica.Services.IServices.IProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final IProductsService productsService;

    @Operation(
            summary = "Register a new product",
            description = "This endpoint registers a new product with name, description and price."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductsResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Product with the same name already exists",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PostMapping("/register-product")
    public ResponseEntity<ProductsResponseDto> registerProduct(
           @Valid @RequestBody ProductRequestDto requestDto){
        ProductsResponseDto responseDto = productsService.registerProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsResponseDto>> getAllProduct(){
        List<ProductsResponseDto> responseDto = this.productsService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(
            summary = "Get a product by ID",
            description = "This endpoint retrieves the details of a product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    @GetMapping("/product/{id}")
    public  ResponseEntity<ProductsResponseDto> getProductById(
            @PathVariable  Integer id){
        ProductsResponseDto responseDto = this.productsService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(
            summary = "Update a product",
            description = "This endpoint updates the details of an existing product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductsResponseDto> updateProduct (@RequestBody UpdateProductRequestDto requestDto, @PathVariable Integer id){
        ProductsResponseDto responseDto = this.productsService.updateProduct(requestDto,id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(
            summary = "Delete a product",
            description = "This endpoint deletes a product by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully",
                    content = @Content(schema = @Schema(implementation = ProductsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ProductsResponseDto> deleteProduct(@PathVariable Integer id){
        ProductsResponseDto responseDto = this.productsService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
