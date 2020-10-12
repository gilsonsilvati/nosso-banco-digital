package br.com.bancodigital.api.domain.model.validation.validator;

import br.com.bancodigital.api.domain.model.validation.annotation.UnicoCPF;
import br.com.bancodigital.api.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnicoCPFValidator implements ConstraintValidator<UnicoCPF, String> {

    @Autowired
    private Clientes clientes;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext validatorContext) {
        return clientes.findByCpf(cpf).isEmpty();
    }

}
