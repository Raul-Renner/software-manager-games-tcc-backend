package com.br.validation;

import com.br.repository.ColumnBoardRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class ColumnBoardValidator implements ConstraintValidator<ValidColumnBoard, Long>  {

    private final ColumnBoardRepository columnBoardRepository;
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(isNull(value)) {
            isValid = false;
        }
        if(!columnBoardRepository.existsById(value)){
            context.buildConstraintViolationWithTemplate("Coluna do board não encontrado.").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
