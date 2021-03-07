package ues.occ.proyeccion.social.ws.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ues.occ.proyeccion.social.ws.app.exceptions.ChangeStatusProjectException;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;
import ues.occ.proyeccion.social.ws.app.model.Violation;
import ues.occ.proyeccion.social.ws.app.model.ViolationErrorResponse;

@ControllerAdvice
public class RestResponseExceptionCatcher extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestResponseExceptionCatcher.class);

	
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> objectNotFound(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), new HttpHeaders(), HttpStatus.OK);
    }
    
    @ExceptionHandler({ChangeStatusProjectException.class})
    public ResponseEntity<Object> ChangeStatusProject(Exception exception, WebRequest webRequest) {
    	log.error("Error al cambiar de estado de un proyecto ", exception);
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), new HttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> invalidParams(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InternalErrorException.class})
    public ResponseEntity<Object> internal(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ViolationErrorResponse response = new ViolationErrorResponse();
        for(FieldError error: ex.getFieldErrors()){
            response.getViolations().add(new Violation(error.getField(), error.getDefaultMessage()));
        }
        return this.handleExceptionInternal(ex, response, headers, status, request);
    }

}
