package com.jotaefi.crud.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jotaefi.crud.exception.InvalidStatusException;

public enum UserStatus {

    ATIVO,
    FERIAS,
    DESLIGADO;

    @JsonCreator
    public static UserStatus fromString(String status) {
        try {
            return valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Status '" + status + "' não existe");
        }
    }
}
