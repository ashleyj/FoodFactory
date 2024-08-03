package org.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ConstraintViolationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<InvalidEntityError> handleConstraintViolationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        InvalidEntityError err = new InvalidEntityError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Argument",
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toSet())
                        .stream().findFirst()
                        .get(),
                request.getRequestURI());


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
