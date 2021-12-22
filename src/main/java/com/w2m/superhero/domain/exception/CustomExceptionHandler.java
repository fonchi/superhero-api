package com.w2m.superhero.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class to handle exceptions for api responses based on custom behaviors
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

  /**
   * handler method of NotFoundException
   * @param e
   * @return
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
    ApiError apiError = new ApiError(e.getStatus().value(), e.getError(), e.getMessage());
    logger.warn("{}", apiError);
    return new ResponseEntity<>(apiError, e.getStatus());
  }

  /**
   * handler method of all other exceptions
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAllException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ApiError apiError = new ApiError(status.value(), "Internal Server Error", e.getMessage());
    logger.error("{}", apiError);
    return new ResponseEntity<>(apiError, status);
  }
}
