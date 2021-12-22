package com.w2m.superhero.infrastructure.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * Class to response api errors
 */
@Getter
@ToString
public class ApiError {

  private int status;
  private String error;
  private String message;

  /**
   * Constructor of API Error based on all parameters
   * @param status
   * @param error
   * @param message
   */
  public ApiError(int status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }
}
