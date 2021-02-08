package ues.occ.proyeccion.social.ws.app.validators;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CarnetRegexValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarnetValidator {
    String message() default "Carnet is an alphanumeric ID with 7 characters only, i.e. AB12345";
}
