package com.br.validation;

import com.br.service.OrganizationService;
import com.br.service.ProjectService;
import com.br.vo.ProjectSaveVO;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.br.fieldQueries.OrganizationFieldQuery.ORGANIZATION_ID;
import static java.util.Arrays.asList;


@RequiredArgsConstructor
public class ProjectSaveVoValidation implements ConstraintValidator<ValidProjectSaveVO, ProjectSaveVO> {

    private final ProjectService projectService;

    private final OrganizationService organizationService;
    @Override
    public void initialize(ValidProjectSaveVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ProjectSaveVO value, ConstraintValidatorContext context) {
        var isValid = true;
        context.disableDefaultConstraintViolation();
        if(!organizationService.existBy(ORGANIZATION_ID.existBy(asList(value.getOrganizationId().toString())))){
            context.buildConstraintViolationWithTemplate("Organização informada não existe.").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
