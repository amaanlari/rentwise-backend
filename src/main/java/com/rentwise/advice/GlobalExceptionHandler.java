package com.rentwise.advice;

import com.rentwise.exception.DuplicateDataException;
import com.rentwise.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message(e.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateDataException(DuplicateDataException e) {
        ApiError apiError = ApiError.builder().status(HttpStatus.CONFLICT).message(e.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        List<String> subErrors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldError = error.getField() + ": " + error.getDefaultMessage();
            subErrors.add(fieldError);
        });

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .subErrors(subErrors)
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiError apiError = ApiError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}