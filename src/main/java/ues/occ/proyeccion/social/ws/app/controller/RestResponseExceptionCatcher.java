package ues.occ.proyeccion.social.ws.app.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ues.occ.proyeccion.social.ws.app.exceptions.InternalErrorException;
import ues.occ.proyeccion.social.ws.app.exceptions.ResourceNotFoundException;

import java.util.Map;

@ControllerAdvice
public class RestResponseExceptionCatcher extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> objectNotFound(Exception exception, WebRequest webRequest) {
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
}
