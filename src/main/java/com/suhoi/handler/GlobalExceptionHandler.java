package com.suhoi.handler;

import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.exception.UserActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserActionException.class)
    public ResponseEntity<String> handleUserActionException(UserActionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<String> handleDataAlreadyExistException(DataAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NoValidDataException.class)
    public ResponseEntity<String> handleNoValidDataException(NoValidDataException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
