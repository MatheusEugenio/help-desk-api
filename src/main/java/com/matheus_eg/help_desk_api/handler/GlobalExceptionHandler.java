package com.matheus_eg.help_desk_api.handler;

import com.matheus_eg.help_desk_api.exception.ErrorReponse;
import com.matheus_eg.help_desk_api.exception.NotFoundException;
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
