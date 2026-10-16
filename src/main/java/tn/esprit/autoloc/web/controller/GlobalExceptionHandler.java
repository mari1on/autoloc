package tn.esprit.autoloc.web.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tn.esprit.autoloc.autolocapi.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(ResourceNotFoundException ex) {
        return Map.of(
                "statut", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage(),
                "horodatage", Instant.now().toString()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(IllegalArgumentException ex) {
        return Map.of(
                "statut", HttpStatus.BAD_REQUEST.value(),
                "message", ex.getMessage(),
                "horodatage", Instant.now().toString()
        );
    }
}
