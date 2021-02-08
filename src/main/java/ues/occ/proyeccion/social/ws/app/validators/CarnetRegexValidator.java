package ues.occ.proyeccion.social.ws.app.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CarnetRegexValidator implements ConstraintValidator<CarnetValidator, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.length()==7){
            var pattern = Pattern.compile("^[A-Za-z]{2}\\d{5}");
            var matcher = pattern.matcher(value);
            return matcher.find();
        }
        return false;
    }
}
