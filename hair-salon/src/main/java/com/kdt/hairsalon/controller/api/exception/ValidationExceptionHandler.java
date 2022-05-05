package com.kdt.hairsalon.controller.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List<ValidationError>> handleValidationExceptions(MethodArgumentNotValidException exception) {

        return ResponseEntity.badRequest().body(exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .collect(Collectors.toList()));
    }

}


