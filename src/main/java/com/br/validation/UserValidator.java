package com.br.validation;

import com.br.repository.UserRepository;
import com.br.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Slf4j
@RequiredArgsConstructor
public class UserValidator implements ConstraintValidator<ValidUser, Long> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ValidUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(isNull(value)) {
            return true;
        }
        return userRepository.existsById(value);
    }
}
