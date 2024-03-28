package com.br.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = OrganizationSaveVoValidator.class)
public @interface ValidOrganizationSaveVO {

    String message() default "Organizacao invalida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}