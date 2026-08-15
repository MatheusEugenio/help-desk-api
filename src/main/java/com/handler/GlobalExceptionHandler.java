package com.handler;

import com.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorReponse> NotFounExceptiondHandler(NotFoundException ex) {
        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorReponse);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorReponse> AlreadyExistsExceptiondHandler(AlreadyExistsException ex) {
        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorReponse);
    }

    @ExceptionHandler(CallCompletedException.class)
    public ResponseEntity<ErrorReponse> CallCompletedExceptionHandler(CallCompletedException ex) {
        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorReponse);
    }

    @ExceptionHandler(CallInactiveException.class)
    public ResponseEntity<ErrorReponse> CallInactiveExceptionHandler(CallInactiveException ex) {
        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorReponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorReponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        ErrorReponse errorReponse = ErrorReponse.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorReponse);
    }
}
