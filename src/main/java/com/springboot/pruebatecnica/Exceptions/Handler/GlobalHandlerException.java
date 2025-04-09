package com.springboot.pruebatecnica.Exceptions.Handler;
import com.springboot.pruebatecnica.Exceptions.Customs.BusinessException;
import com.springboot.pruebatecnica.Exceptions.Customs.ProductAlreadyExistsException;
import com.springboot.pruebatecnica.Exceptions.Customs.ProductoNotFoundException;
import com.springboot.pruebatecnica.Exceptions.Dtos.ErrorResponse;
import com.springboot.pruebatecnica.Exceptions.Dtos.ErrorsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

    //lanzadores

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorsResponse errorResponse = new ErrorsResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ErrorResponse> ProductNotFoundExceptionHandler(ProductoNotFoundException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .message("Error: this product could not be found, check your request")
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> ProductNotFoundExceptionHandler(ProductAlreadyExistsException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .message("The product with this name already exists, try with another name")
                .code(HttpStatus.CONFLICT.value())
                .date(LocalDate.now())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public  ResponseEntity<ErrorResponse> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .message("Please check your JSON syntax. Field names and values must be in double quotes.")
                .code(HttpStatus.BAD_REQUEST.value())
                .date(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .message("there was a server error, please try again later.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .date(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(BusinessException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(exception.getMessage())
                .message("there was a server error, please try again later.")
                .code(HttpStatus.BAD_REQUEST.value())
                .date(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
