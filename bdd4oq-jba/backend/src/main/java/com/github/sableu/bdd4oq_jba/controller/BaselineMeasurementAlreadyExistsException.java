package com.github.sableu.bdd4oq_jba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BaselineMeasurementAlreadyExistsException extends RuntimeException {
    public BaselineMeasurementAlreadyExistsException(String message) {
        super(message);
    }
}
