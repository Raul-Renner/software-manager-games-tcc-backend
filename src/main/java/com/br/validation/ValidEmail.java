package com.br.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, PARAMETER})
@Constraint(validatedBy = EmailValidator.class)
public @interface ValidEmail {
    String message() default "email ja utilizado, por gentileza escolha outro.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
