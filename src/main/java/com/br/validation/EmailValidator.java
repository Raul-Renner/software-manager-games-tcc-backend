package com.br.validation;

import com.br.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import static com.br.fieldQueries.UserFieldQuery.EMAIL;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(isNull(value) || value.isBlank()){
            return true;
        }

        return !userRepository.exists(EMAIL.existBy(singletonList(value)));
    }
}
