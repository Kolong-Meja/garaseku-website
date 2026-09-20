package com.faisalrmdhn.GaraseKu.handlers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.naming.ServiceUnavailableException;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import com.faisalrmdhn.GaraseKu.exception.AccessDeniedException;
import com.faisalrmdhn.GaraseKu.exception.BadGatewayException;
import com.faisalrmdhn.GaraseKu.exception.RequestTooLargeException;
import com.faisalrmdhn.GaraseKu.exception.ResourceNotFoundException;
import com.faisalrmdhn.GaraseKu.exception.TooManyRequestsException;
import com.faisalrmdhn.GaraseKu.exception.UnauthorizedException;
import com.faisalrmdhn.GaraseKu.exception.UnprocessableEntityException;
import com.faisalrmdhn.GaraseKu.exception.UnsupportedMediaTypeException;

@RestControllerAdvice
public class ApiExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(BadRequestException.class)
  public ProblemDetail handleBadRequestException(BadRequestException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ProblemDetail handleUnauthorizedException(UnauthorizedException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(MethodNotAllowedException.class)
  public ProblemDetail handleMethodNotAllowedException(MethodNotAllowedException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(RequestTooLargeException.class)
  public ProblemDetail handleRequestTooLargeException(RequestTooLargeException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONTENT_TOO_LARGE,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(UnsupportedMediaTypeException.class)
  public ProblemDetail handleUnsupportedMediaTypeException(UnsupportedMediaTypeException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ProblemDetail handleUnprocessableEntityException(UnprocessableEntityException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(TooManyRequestsException.class)
  public ProblemDetail handleTooManyRequestsException(TooManyRequestsException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.TOO_MANY_REQUESTS,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleInternalServerErrorException(Exception exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(BadGatewayException.class)
  public ProblemDetail handleBadGatewayException(BadGatewayException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  public ProblemDetail handleServiceUnavailableException(ServiceUnavailableException exception) {
    LOG.error(exception.getMessage(), exception);

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,
        exception.getMessage());
    problemDetail.setProperty("timestamps",
        LocalDateTime.now(ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    return problemDetail;
  }
}