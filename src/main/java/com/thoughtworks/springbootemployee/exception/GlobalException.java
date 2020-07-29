package com.thoughtworks.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompanyNotFoundException.class)
    void CompanyNotFoundException(){

    }
}
