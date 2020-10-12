package br.com.bancodigital.api.domain.model.validation.validator;

import br.com.bancodigital.api.domain.model.validation.annotation.MaiorIdade;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MaiorIdadeValidator implements ConstraintValidator<MaiorIdade, LocalDate> {

    @Override
    public boolean isValid(LocalDate nascimento, ConstraintValidatorContext validatorContext) {
        return getIdade(nascimento) >= 18;
    }

    private long getIdade(LocalDate nascimento) {
        return ChronoUnit.YEARS.between(nascimento, LocalDate.now());
    }

}
