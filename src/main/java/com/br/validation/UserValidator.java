package com.br.validation;

import com.br.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import static java.util.Objects.isNull;


@RequiredArgsConstructor
public class UserValidator implements ConstraintValidator<ValidUser, Long> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ValidUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(isNull(value)) {
            isValid = false;
        }
        if(!userRepository.existsById(value)){
            context.buildConstraintViolationWithTemplate("Usuário não encontrado.").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
