package ues.occ.proyeccion.social.ws.app.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


public class ValidateService {
	
	@Autowired
	Validator validator;

	
	@Bean
	public void validate(Object validateObject){
		Set<ConstraintViolation<Object>> violations = validator.validate(validateObject);
		
		String result = violations.stream().map(violation -> violation.getMessage()).reduce("", (initial, element) -> initial + element + "\n");
		
		if(!violations.isEmpty()) {
			throw new IllegalArgumentException(result);
		}
	}
	
	
}
