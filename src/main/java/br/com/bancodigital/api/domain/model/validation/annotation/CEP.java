package br.com.bancodigital.api.domain.model.validation.annotation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "(\\d{5}-\\d{3})?")
public @interface CEP {
	
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "CEP deve seguir o padrão 99999-999";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
