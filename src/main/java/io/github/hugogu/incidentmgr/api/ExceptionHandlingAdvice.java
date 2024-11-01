package io.github.hugogu.incidentmgr.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.util.StringUtils.collectionToDelimitedString;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResponse handException(IllegalStateException e) {
        log.warn("ExceptionHandler hands IllegalStateException", e);
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ErrorResponse handValidationException(ValidationException e) {
        log.info("ExceptionHandler hands ViolationException", e);
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handConstraintValidationException(ConstraintViolationException e) {
        log.info("ExceptionHandler hands ConstraintViolationException", e);
        String message = collectionToDelimitedString(
                e.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() + ":" + cv.getMessage()).toList(), "\r\n");
        return ErrorResponse.create(e, HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handNotFoundException(EntityNotFoundException e) {
        log.info("ExceptionHandler hands EntityNotFoundException", e);
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}
