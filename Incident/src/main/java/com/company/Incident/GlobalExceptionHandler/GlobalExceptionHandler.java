package com.company.Incident.GlobalExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.Incident.exception.BadRequestExceptionHandler;
import com.company.Incident.exception.ResourceNotFoundException;
import com.company.Incident.payload.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> ResourceNotFoundException(ResourceNotFoundException ex) {

        log.warn("GlobalExceptionHandler::ResourceNotFoundException::Resource Not Found Exception: {}",
                ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return new ResponseEntity<ApiResponse<Object>>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestExceptionHandler.class)
    public ResponseEntity<ApiResponse<Object>> BadRequestExceptionHandler(BadRequestExceptionHandler ex) {
        log.warn("GlobalExceptionHandler::BadRequestExceptionHandler::Bad Request Exception: {}", ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiResponse<Object>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> AccessDeniedException(AccessDeniedException ex) {
        log.warn("GlobalExceptionHandler::AccessDeniedException::Access Denied Exception : {}", ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(false,
                ex.getMessage(),
                null,
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiResponse<Object>>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> MethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        log.warn("GlobalExceptionHandler::MethodArgumentNotValidException::Validation Error : {}", ex.getMessage());

        Map<String, String> errors = new java.util.HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        ApiResponse<Map<String, String>> response = new ApiResponse<>(false,
                "Validation failed for one or more fields",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiResponse<Map<String, String>>>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        log.error("GlobalExceptionHandler::handleException::Internal Server Error : {}", ex.getMessage(), ex);

        ApiResponse<Object> response = new ApiResponse<>(false,
                "An unexpected error occurred. Please try again later.",
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return new ResponseEntity<ApiResponse<Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
