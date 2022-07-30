package com.devsus.challenge.controller;

import com.devsus.challenge.dto.response.ApiErrorResponse;
import com.devsus.challenge.exception.IdNotFoundException;
import com.devsus.challenge.exception.InsufficientBalanceException;
import com.devsus.challenge.exception.MaxExtractionPerDayException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IdNotFoundException.class)
    protected ResponseEntity<Object> handleIdNotFound(RuntimeException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                Arrays.asList(ex.getMessage())
        );
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class, MaxExtractionPerDayException.class})
    protected ResponseEntity<Object> handleValidation(RuntimeException ex, WebRequest request) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                ex.getMessage(),
                Arrays.asList(ex.getMessage())
        );
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }
}