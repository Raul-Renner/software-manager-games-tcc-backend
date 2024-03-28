package com.br.validation;

import com.br.service.OrganizationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.br.fieldQueries.OrganizationFieldQuery.ORGANIZATION_ID;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class OrganizationValidator implements ConstraintValidator<ValidOrganization, Long> {

    private final OrganizationService organizationService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        if(isNull(value)){
            return false;
        }
        return organizationService.existBy(ORGANIZATION_ID.existBy(singletonList(value.toString())));
    }
}
