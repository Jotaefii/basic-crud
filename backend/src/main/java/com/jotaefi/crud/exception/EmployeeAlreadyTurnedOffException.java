package com.jotaefi.crud.exception;

public class EmployeeAlreadyTurnedOffException extends RuntimeException {
    public EmployeeAlreadyTurnedOffException(String message) {
        super(message);
    }
}
