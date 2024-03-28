package com.br.validation;


import com.br.repository.OrganizationRepository;
import com.br.service.OrganizationService;
import com.br.vo.OrganizationSaveVO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


import static com.br.fieldQueries.OrganizationFieldQuery.EMAIL;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@RequiredArgsConstructor
public class OrganizationSaveVoValidator implements ConstraintValidator<ValidOrganizationSaveVO, OrganizationSaveVO> {

    private final OrganizationRepository organizationRepository;

    private final OrganizationService organizationService;

    @Override
    public void initialize(ValidOrganizationSaveVO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrganizationSaveVO value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        var isValid = true;
        if(isNull(value.getName()) || isBlank(value.getName())){
            context.buildConstraintViolationWithTemplate("O Nome da organização deve ser preenchido.").addConstraintViolation();
            isValid = false;
        }else if(organizationService.existBy(EMAIL.existBy(asList(value.getEmail())))){
            context.buildConstraintViolationWithTemplate("Email já cadastrado no nosso sistema.").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
