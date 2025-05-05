package com.backend.springcloud.mcsv.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlerIllegalStateException(IllegalStateException illegalStateException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no encontrado: " + illegalStateException.getMessage());

        return mensaje;
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no procesado: " + resourceNotFoundException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no procesado: " + illegalArgumentException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({DuplicateUserException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlerDuplicateUserException(DuplicateUserException duplicateUserException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no procesado: " + duplicateUserException.getMessage());

        return mensaje;
    }

}
