package com.kdt.hairsalon.controller.api.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
class ValidationError {
    String field;
    String defaultMessage;

    public ValidationError(String field, String defaultMessage) {
        this.field = field;
        this.defaultMessage = defaultMessage;

    }

    public ValidationError() {
    }

    public static ValidationError of(FieldError fieldError) {
        return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
}