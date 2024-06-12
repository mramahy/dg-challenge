package com.dataguard.dgchallenge.error;

import org.springframework.http.HttpStatus;

public class SuperheroException extends RuntimeException{
  private final HttpStatus status;

  public SuperheroException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
