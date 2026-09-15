package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EmployeeCreateDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @Positive
        BigDecimal salary,
        @NotNull
        Long departmentId
) {
}
