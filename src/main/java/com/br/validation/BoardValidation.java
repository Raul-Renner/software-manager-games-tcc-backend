package com.br.validation;

import com.br.service.BoardService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.br.fieldQueries.BoardFieldQuery.ID;
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
        if(isNull(value)){
            return true;
        }
        return boardService.existBy(ID.existBy(singletonList(value.toString())));    }
}
