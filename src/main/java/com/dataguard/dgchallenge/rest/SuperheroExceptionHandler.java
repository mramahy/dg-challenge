package com.dataguard.dgchallenge.rest;

import com.dataguard.dgchallenge.error.SuperheroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SuperheroExceptionHandler {

  @ExceptionHandler(SuperheroException.class)
  public ResponseEntity<String> handleSuperheroException(SuperheroException exception) {
    return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
  }

}
