package io.github.hugogu.incidentmgr.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handException(IllegalStateException e) {
        log.warn("ExceptionHandler hands IllegalStateException", e);
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handConstraintException(ValidationException e) {
        log.warn("ExceptionHandler hands ConstraintViolationException", e);
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handNotFoundException(EntityNotFoundException e) {
        log.warn("ExceptionHandler hands ConstraintViolationException", e);
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}
