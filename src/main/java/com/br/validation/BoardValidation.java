package com.br.validation;

import com.br.service.BoardService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.br.fieldQueries.BoardFieldQuery.ID;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;


@RequiredArgsConstructor
public class BoardValidation implements ConstraintValidator<ValidBoard, Long> {

    private final BoardService boardService;
    @Override
    public void initialize(ValidBoard constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(isNull(value)) {
            isValid = false;
        }
        if(!boardService.existBy(ID.existBy(asList(value.toString())))){
            context.buildConstraintViolationWithTemplate("Board não encontrado.").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
