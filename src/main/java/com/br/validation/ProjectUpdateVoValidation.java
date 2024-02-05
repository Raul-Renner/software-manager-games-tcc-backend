package com.br.validation;

import com.br.service.ProjectService;
import com.br.vo.ProjectSaveVO;
import com.br.vo.ProjectUpdateVO;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.br.fieldQueries.ProjectFieldQuery.ORGANIZATION_ID_PROJECT_ID;
import static com.br.fieldQueries.ProjectFieldQuery.PROJECT_ID;
import static java.util.Arrays.asList;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class ProjectUpdateVoValidation implements ConstraintValidator<ValidProjectUpdateVO, ProjectUpdateVO> {

    private final ProjectService projectService;

    @Override
    public void initialize(ValidProjectUpdateVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ProjectUpdateVO value, ConstraintValidatorContext context) {
        var isValid = true;
        if(isNull(value.getId()) || isNull(value.getOrganizationId()) || isBlank(value.getName()) ||
                isBlank(value.getDescription())) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        if(!projectService.existBy(PROJECT_ID.existBy(asList(value.getId().toString())))) {
            context.buildConstraintViolationWithTemplate("Projeto não encontrado.").addConstraintViolation();
            isValid = false;
        }else if(!projectService.existBy(ORGANIZATION_ID_PROJECT_ID.existBy(asList(value.getId().toString(), value.getOrganizationId().toString())))){
            context.buildConstraintViolationWithTemplate("O Projeto não pertence a organizacao informada.").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
