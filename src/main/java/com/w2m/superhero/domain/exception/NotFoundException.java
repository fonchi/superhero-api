package com.w2m.superhero.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

  private HttpStatus status = HttpStatus.NOT_FOUND;
  private String error = "Resource not found";
  private String message;

  public NotFoundException(String message) {
    super(message);
    this.message = message;
  }
}
