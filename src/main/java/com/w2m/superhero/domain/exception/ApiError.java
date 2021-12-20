package com.w2m.superhero.domain.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiError {

  private int status;
  private String error;
  private String message;

  public ApiError(int status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }
}
