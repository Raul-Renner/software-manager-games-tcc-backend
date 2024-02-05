package com.br.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
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

    String message() default "Organizacao e invalida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}