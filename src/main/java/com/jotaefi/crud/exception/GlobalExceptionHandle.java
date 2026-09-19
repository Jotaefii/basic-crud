package com.jotaefi.crud.exception;

import com.jotaefi.crud.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> HandleBadRequestExcpetion(BadRequestException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(List.of(e.getMessage()))
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> HandleNotFounException(NotFoundException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(List.of(e.getMessage()))
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorResponseDTO> HandleInvalidStatusException(InvalidStatusException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(List.of(e.getMessage()))
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmployeeAlreadyTurnedOffException.class)
    public ResponseEntity<ErrorResponseDTO> HandleInvalidStatusException(EmployeeAlreadyTurnedOffException e) {
        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(List.of(e.getMessage()))
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorResponseDTO response = ErrorResponseDTO.builder()
                .message(errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
