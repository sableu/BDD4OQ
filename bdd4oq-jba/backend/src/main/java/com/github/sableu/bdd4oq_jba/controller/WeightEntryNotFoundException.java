package com.github.sableu.bdd4oq_jba.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeightEntryNotFoundException extends RuntimeException {
    public WeightEntryNotFoundException(String message){
        super(message);
    }
}
