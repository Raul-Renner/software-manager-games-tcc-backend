package com.br.validation;

import com.br.vo.ActivitySaveVO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ActivityVoValidator implements ConstraintValidator<ValidActivitySaveVO, ActivitySaveVO> {

    @Override
    public boolean isValid(ActivitySaveVO value, ConstraintValidatorContext context) {
        return false;
    }
}
