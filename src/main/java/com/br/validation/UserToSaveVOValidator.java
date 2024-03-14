package com.br.validation;

import com.br.service.OrganizationService;
import com.br.service.ProjectService;
import com.br.service.UserService;
import com.br.vo.UserSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.br.fieldQueries.OrganizationFieldQuery.ORGANIZATION_ID;

import static java.util.Arrays.asList;


@Slf4j
@RequiredArgsConstructor
public class UserToSaveVOValidator implements ConstraintValidator<ValidUserToSaveVO, UserSaveVO> {

    private final OrganizationService organizationService;

    private final UserService userService;

    private final ProjectService projectService;

    @Override
    public void initialize(ValidUserToSaveVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserSaveVO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(!organizationService.existBy(ORGANIZATION_ID.existBy(asList(value.getOrganizationId().toString())))){
            context.buildConstraintViolationWithTemplate("Organização informada não existe.").addConstraintViolation();
            isValid = false;
        }
//        else if(userService.existBy(LOGIN.existBy(asList(value.getLogin())))){
//            context.buildConstraintViolationWithTemplate("o nome para login já existe no nosso sistema.").addConstraintViolation();
//            isValid = false;
//        }
        return isValid;
    }
}
