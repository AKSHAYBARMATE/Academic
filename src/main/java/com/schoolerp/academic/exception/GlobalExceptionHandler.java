package com.schoolerp.academic.exception;

import com.schoolerp.academic.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    List<ApiErrorResponse.FieldValidationError> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                err ->
                    new ApiErrorResponse.FieldValidationError(
                        err.getField(), err.getDefaultMessage()))
            .collect(Collectors.toList());

    ApiErrorResponse response =
        ApiErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .message("Validation failed")
            .path(request.getRequestURI())
            .errors(fieldErrors)
            .build();

    log.error(
        "Validation failed at URI: {} - Errors: {}", request.getRequestURI(), fieldErrors, ex);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiErrorResponse> handleRuntimeException(
      RuntimeException ex, HttpServletRequest request) {
    ApiErrorResponse errorResponse = new ApiErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
    errorResponse.setError("Not Found");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());
    errorResponse.setErrors(null);

    log.error(
        "RuntimeException at URI: {} - Message: {}", request.getRequestURI(), ex.getMessage(), ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericException(
      Exception ex, HttpServletRequest request) {
    ApiErrorResponse errorResponse = new ApiErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorResponse.setError("Internal Server Error");
    errorResponse.setMessage(ex.getMessage());
    errorResponse.setPath(request.getRequestURI());
    errorResponse.setErrors(null);

    log.error(
        "Unhandled Exception at URI: {} - Message: {}",
        request.getRequestURI(),
        ex.getMessage(),
        ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiErrorResponse> handleCustomException(
      CustomException ex, HttpServletRequest request) {
    ApiErrorResponse errorResponse = new ApiErrorResponse();
    errorResponse.setTimestamp(LocalDateTime.now());
    errorResponse.setStatus(ex.getStatusCode());
    errorResponse.setError(HttpStatus.valueOf(ex.getStatusCode()).getReasonPhrase());
    errorResponse.setMessage(ex.getErrorMessage());
    errorResponse.setPath(request.getRequestURI());

    log.error(
        "CustomException at URI: {} - Status: {} - Message: {}",
        request.getRequestURI(),
        ex.getStatusCode(),
        ex.getErrorMessage(),
        ex);
    return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode()));
  }
}
