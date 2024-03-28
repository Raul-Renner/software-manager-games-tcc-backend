package com.br.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;



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
