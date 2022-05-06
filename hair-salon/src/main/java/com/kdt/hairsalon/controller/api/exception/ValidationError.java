package com.kdt.hairsalon.controller.api.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
class ValidationError {
    String field;
    String message;

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;

    }

    public ValidationError() {
    }

    public static ValidationError of(FieldError fieldError) {
        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}