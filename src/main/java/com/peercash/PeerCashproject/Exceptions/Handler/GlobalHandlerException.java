package com.peercash.PeerCashproject.Exceptions.Handler;

import com.peercash.PeerCashproject.Exceptions.Custom.*;
import com.peercash.PeerCashproject.Exceptions.Dto.ErrorResponse;
import com.peercash.PeerCashproject.Exceptions.Dto.ErrorsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {
    //lanzadores
@ExceptionHandler(UserNotFondException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler(UserNotFondException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("USER NOT FOUND")
                .error(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> UserAlreadyExistExceptionHandler(UserAlreadyExistException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("USER ALREADY EXIST")
                .error(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> roleNotFoundExceptionHandler(RoleNotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("ROLE NOT FOUND")
                .error(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.value())
                .date(LocalDate.now())
                .build();
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> validationExceptionsHandler(MethodArgumentNotValidException exception){
    Map<String, String> errors = new HashMap<>();

    exception.getBindingResult().getAllErrors().forEach(error -> {
        String fieldError = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldError,errorMessage);
    });

    ErrorsResponse errorsResponse = ErrorsResponse.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.BAD_REQUEST.value())
            .message("VALIDATION_ERROR")
            .errors(errors)
            .build();
    return new ResponseEntity<>(errorsResponse,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IBadRequestExceptions.class)
    public  ResponseEntity<ErrorResponse> badRequestExceptionHandler(IBadRequestExceptions exceptions){
          ErrorResponse errorResponse = ErrorResponse.builder()
                  .code(HttpStatus.BAD_REQUEST.value())
                  .error(exceptions.getMessage())
                  .message("BAD_REQUEST")
                  .date(LocalDate.now())
                  .build();
          return  new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public  ResponseEntity<ErrorResponse> unauthorizedActionExceptionHandler(UnauthorizedActionException exceptions){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .error(exceptions.getMessage())
                .message("UNAUTHORIZED")
                .date(LocalDate.now())
                .build();
        return  new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity<ErrorResponse> unauthorizedActionExceptionHandler(EntityNotFoundException exceptions){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .error(exceptions.getMessage())
                .message("NOT_FOUND_ENTITY")
                .date(LocalDate.now())
                .build();
        return  new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
