package com.br.validation;

import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.List.of;

@RequiredArgsConstructor
public class ActivityValidator  implements ConstraintValidator<ValidActivity, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
//        if (nonNull(frequencyService.findBy(valueOf("ID").findBy(of(value.toString()))))) {
//            return true;
//        }else{
//
//        }
        return true;
    }
}
