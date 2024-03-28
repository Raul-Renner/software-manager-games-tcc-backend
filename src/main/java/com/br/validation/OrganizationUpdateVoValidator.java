package com.br.validation;


import com.br.service.OrganizationService;
import com.br.vo.OrganizationUpdateVO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import static com.br.fieldQueries.OrganizationFieldQuery.ORGANIZATION_ID;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class OrganizationUpdateVoValidator implements ConstraintValidator<ValidOrganizationUpdateVO, OrganizationUpdateVO> {

    private final OrganizationService organizationService;

    @Override
    public void initialize(ValidOrganizationUpdateVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrganizationUpdateVO value, ConstraintValidatorContext context) {
        var isValid = true;
        if(isNull(value.getId()) || isBlank(value.getName())) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        if(!organizationService.existBy(ORGANIZATION_ID.existBy(asList(value.getId().toString())))) {
            context.buildConstraintViolationWithTemplate("Organizacao não encontrado.").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
