package com.handler;

import com.exception.ErrorReponse;
import com.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorReponse> NotFoundHandler(NotFoundException ex) {

        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(404)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorReponse);
    }
}
