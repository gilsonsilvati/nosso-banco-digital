package br.com.bancodigital.api.domain.model.validation.annotation;

import br.com.bancodigital.api.domain.model.validation.validator.UnicoEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UnicoEmailValidator.class)
public @interface UnicoEmail {

    String message() default "Já existe um cliente cadastrado com este email";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
