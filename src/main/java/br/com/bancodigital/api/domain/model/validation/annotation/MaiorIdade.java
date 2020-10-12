package br.com.bancodigital.api.domain.model.validation.annotation;

import br.com.bancodigital.api.domain.model.validation.validator.MaiorIdadeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaiorIdadeValidator.class)
public @interface MaiorIdade {

    String message() default "Você precisa ser maior de 18 anos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
