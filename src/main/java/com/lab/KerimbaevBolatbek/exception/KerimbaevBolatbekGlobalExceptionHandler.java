package com.lab.KerimbaevBolatbek.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class KerimbaevBolatbekGlobalExceptionHandler {

    // 404 - resource not found
    @ExceptionHandler(KerimbaevBolatbekResourceNotFoundException.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleNotFound(
            KerimbaevBolatbekResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    // 400 - bad request (business logic errors)
    @ExceptionHandler(KerimbaevBolatbekBadRequestException.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleBadRequest(
            KerimbaevBolatbekBadRequestException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    // 400 - validation errors (@Valid failures)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    // 401 - bad credentials (login fails)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password", request, null);
    }

    // 403 - access denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Access denied", request, null);
    }

    // 500 - any other unhandled exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<KerimbaevBolatbekErrorResponse> handleAll(
            Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
    }

    private ResponseEntity<KerimbaevBolatbekErrorResponse> buildResponse(
            HttpStatus status, String message, HttpServletRequest request, Map<String, String> validationErrors) {
        KerimbaevBolatbekErrorResponse response = new KerimbaevBolatbekErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                validationErrors
        );
        return new ResponseEntity<>(response, status);
    }
}