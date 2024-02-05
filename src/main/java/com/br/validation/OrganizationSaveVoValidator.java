package com.br.validation;


import com.br.vo.OrganizationSaveVO;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class OrganizationSaveVoValidator implements ConstraintValidator<ValidOrganizationSaveVO, OrganizationSaveVO> {
    @Override
    public void initialize(ValidOrganizationSaveVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrganizationSaveVO value, ConstraintValidatorContext context) {
        if(isNull(value.getName()) || isBlank(value.getName())){
            return true;
        }
        return false;
    }
}
