package com.anhtq.app.admin.excetion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handlerRuntimeException(Exception serviceException) {
    log.error(serviceException.toString());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder().message(serviceException.getMessage()).build());
  }

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<ErrorResponse> handlerApiException(ApiException apiException) {
    log.error(apiException.toString());
    return ResponseEntity.status(apiException.getHttpStatus())
        .body(ErrorResponse.builder().message(apiException.getMessage()).build());
  }
}
