package com.w2m.superhero.infrastructure.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
    String message = String.format("message: %s, class: %s, cause: %s", e.getMessage(),
        e.getClass().getName(), e.getCause());
    ApiError apiError = new ApiError(status.value(), "Internal Server Error", message);
    logger.error("{}, stacktrace: {}", apiError, getStackTrace(e));
    return new ResponseEntity<>(apiError, status);
  }

  /**
   * handler method of validation exceptions
   * @param e
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    StringBuilder sb = new StringBuilder();
    e.getBindingResult().getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()));
    ApiError apiError = new ApiError(status.value(), "Validation Failed", e.getMessage());
    return new ResponseEntity(apiError, status);
  }

  private String getStackTrace(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }
}
