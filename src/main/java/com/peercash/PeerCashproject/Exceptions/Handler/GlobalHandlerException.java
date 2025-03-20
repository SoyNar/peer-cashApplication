package com.peercash.PeerCashproject.Exceptions.Handler;

import com.peercash.PeerCashproject.Exceptions.Custom.RoleNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserAlreadyExistException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Exceptions.Dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalHandlerException {
    //lanzadores
@ExceptionHandler(UserNotFondException.class)
    private ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UserNotFondException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("USER NOT FOUND")
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    private ResponseEntity<ErrorResponse> UserAlreadyExistExceptionHandler(UserAlreadyExistException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("USER ALREADY EXIST")
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<ErrorResponse> roleNotFoundExceptionHandler(RoleNotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("ROLE NOT FOUND")
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
