package com.br.validation;

import com.br.service.ProjectService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;



import static com.br.fieldQueries.ProjectFieldQuery.PROJECT_ID;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class ProjectValidator implements ConstraintValidator<ValidProject, Long> {

    private final ProjectService projectService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if (isNull(projectService.findProjectById(value))) {
            context.buildConstraintViolationWithTemplate("Um dos projetos informados não existe ou não faz parte dessa organização.").addConstraintViolation();
            isValid = false;
        }else if(!projectService.existBy(PROJECT_ID.existBy(asList(String.valueOf(value))))){
            context.buildConstraintViolationWithTemplate("Projeto não encontrado.").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}