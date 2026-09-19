package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EmployeeCreateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Positive(message = "Salário deve ser maior que zero")
        BigDecimal salary,

        @NotNull(message = "Id do departamento é obrigatório")
        Long departmentId
) {
}
