package com.br.validation;

import com.br.vo.ActivitySaveVO;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ActivityVoValidator implements ConstraintValidator<ValidActivitySaveVO, ActivitySaveVO> {

    @Override
    public boolean isValid(ActivitySaveVO value, ConstraintValidatorContext context) {
        return false;
    }
}
