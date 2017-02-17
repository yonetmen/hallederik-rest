package com.kasimgul.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ConstraintValidationException extends ValidationException implements Serializable {
    private static final long SerialVersionUID = 1L;

    public ConstraintValidationException() {
    }

    public ConstraintValidationException(String message) {
        super(message);
    }

}
