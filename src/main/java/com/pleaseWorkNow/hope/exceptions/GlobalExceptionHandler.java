package com.pleaseWorkNow.hope.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice // For every exception thrown in the application, this class will handle
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class) // This method will handle AccessDeniedException
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex){
        String message = "You do not have permission to access this resource";
        return new ResponseEntity<>(message, FORBIDDEN);
    }
}
