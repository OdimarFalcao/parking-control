package com.api.parkingcontrol.handler;

import com.api.parkingcontrol.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)  
    public ResponseEntity<ExceptionDetails>
    handlerBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("BadRequestException, check the documentation")
                        .details(badRequestException.getMessage())
                        .developerMessage(badRequestException.getClass().getName())
                        .build(),HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails>
    handlerMethodArgumentNotValidException(MethodArgumentNotValidException argumentNotValidException){

        List<FieldError> fieldErrors = argumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("BadRequestException, Invalid Fields")
                        .details("Checked Fields erros")
                        .developerMessage(argumentNotValidException.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(GenericExceptionNotFound.class)
    public ResponseEntity<ExceptionDetails>
    NotFoundException(GenericExceptionNotFound ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("BadRequestException, check the documentation")
                        .details(ex.getMessage())
                        .developerMessage(ex.getMessage())
                        .build(),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(GenericConflictException.class)
    public ResponseEntity<ExceptionDetails>
    ConflictException(GenericConflictException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("BadRequestException, check the documentation")
                        .details(ex.getMessage())
                        .developerMessage(ex.getMessage())
                        .build(),HttpStatus.NOT_FOUND);

    }

}
