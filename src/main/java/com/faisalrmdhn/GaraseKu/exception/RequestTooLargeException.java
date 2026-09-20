package com.faisalrmdhn.GaraseKu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONTENT_TOO_LARGE)
public class RequestTooLargeException extends RuntimeException {
  public RequestTooLargeException(String message) {
    super(message);
  }
}
