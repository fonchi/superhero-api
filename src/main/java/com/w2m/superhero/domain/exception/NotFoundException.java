package com.w2m.superhero.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("resource not found");
  }

  public NotFoundException(String message) {
    super(message);
  }
}
