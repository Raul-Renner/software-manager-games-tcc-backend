package com.br.validation;

import jakarta.validation.Constraint;

import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({METHOD,FIELD,CONSTRUCTOR,PARAMETER,TYPE_USE})
@Constraint(validatedBy = BoardValidation.class)
public @interface ValidBoard {
    String message() default "Board inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
