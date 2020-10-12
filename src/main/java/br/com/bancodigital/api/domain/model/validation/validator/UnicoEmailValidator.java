package br.com.bancodigital.api.domain.model.validation.validator;

import br.com.bancodigital.api.domain.model.validation.annotation.UnicoEmail;
import br.com.bancodigital.api.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnicoEmailValidator implements ConstraintValidator<UnicoEmail, String> {

    @Autowired
    private Clientes clientes;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return clientes.findByEmail(email).isEmpty();
    }

}
