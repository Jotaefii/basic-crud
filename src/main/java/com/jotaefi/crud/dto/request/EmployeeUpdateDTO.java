package com.jotaefi.crud.dto.request;

import com.jotaefi.crud.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EmployeeUpdateDTO(
        String name,

        @Email
        String email,

        @Positive
        BigDecimal salary,

        EmployeeStatus status
) {
}
