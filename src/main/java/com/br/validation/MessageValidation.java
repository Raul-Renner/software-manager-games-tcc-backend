package com.br.validation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class MessageValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;

    private final String message;

    public MessageValidation(ObjectError error) {
        if(error instanceof FieldError) {
            this.fieldName = ((FieldError) error).getField();
        } else {
            this.fieldName = error.getObjectName();
        }
        this.message = error.getDefaultMessage();
    }

    public MessageValidation(ConstraintViolation error) {
        this.message = error.getMessage();
        this.fieldName = error.getPropertyPath().toString();
    }

    public MessageValidation(String message) {
        this.message = message;
    }
}

