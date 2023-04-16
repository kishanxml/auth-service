package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage handleServiceException(ServiceException ex) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            ex.getMessage());
    return message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<String> fieldErrors = result.getFieldErrors().stream()
            .map(f -> f.getField() +":"+ f.getDefaultMessage())
            .collect(Collectors.toList());
    ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            "Request argument not valid:"+fieldErrors);
    return message;
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage usernameNotFoundException(UsernameNotFoundException ex) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            ex.getMessage());
    return message;
  }

  @ExceptionHandler(UsernameAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage usernameAlreadyExistException(UsernameAlreadyExistException ex) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage());
    return message;
  }

  @ExceptionHandler(InvalidCredentialException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorMessage invalidCredentialException(InvalidCredentialException ex) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            new Date(),
            ex.getMessage());
    return message;
  }

}