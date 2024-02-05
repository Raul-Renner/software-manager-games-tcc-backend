package com.br.validation;

import com.br.repository.UserRepository;
import com.br.service.ProjectService;
import com.br.service.UserService;
import com.br.vo.UserUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static com.br.fieldQueries.UserFieldQuery.*;
import static java.util.Arrays.asList;

@Slf4j
@RequiredArgsConstructor
public class UserToUpdateVOValidator implements ConstraintValidator<ValidUserToUpdateVO, UserUpdateVO> {

    private final ProjectService projectService;

    private final UserRepository userRepository;

    private final UserService userService;

    @Override
    public void initialize(ValidUserToUpdateVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateVO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(!userService.existBy(USER_ID_ORGANIZATION_ID.existBy(asList(value.getId().toString(),value.getOrganizationId().toString())))){
            context.buildConstraintViolationWithTemplate("Organização informada não existe.").addConstraintViolation();
            isValid = false;
        }else if(!userRepository.exists(ID_EMAIL.existBy(List.of(value.getId().toString(), value.getEmail()))) &&
                userService.existBy(EMAIL.existBy(asList(value.getEmail())))){
            context.buildConstraintViolationWithTemplate("Email já cadastrado no nosso sistema.").addConstraintViolation();
            isValid = false;
        }else if(!userService.existBy(ID_LOGIN.existBy(List.of(value.getId().toString(), value.getLogin())))){
            if(userService.existBy(LOGIN.existBy(asList(value.getLogin())))) {
                context.buildConstraintViolationWithTemplate("o nome para login já existe no nosso sistema.").addConstraintViolation();
                isValid = false;
            }

        }
        return isValid;
    }
}
