package com.br.validation;

import com.br.service.ActivityService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.br.fieldQueries.ActivityFieldQuery.ID;
import static com.br.fieldQueries.ProjectFieldQuery.PROJECT_ID;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;


@RequiredArgsConstructor
public class ActivityValidator  implements ConstraintValidator<ValidActivity, Long> {

    private final ActivityService activityService;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if (isNull(activityService.findById(value))) {
            context.buildConstraintViolationWithTemplate("A atividade informada não existe ou não faz parte dessa organização.").addConstraintViolation();
            isValid = false;
        }else if(!activityService.existBy(ID.existBy(asList(String.valueOf(value))))){
            context.buildConstraintViolationWithTemplate("Atividade não encontrado.").addConstraintViolation();
            isValid = false;
        }
        return isValid;

    }
}
