package com.jotaefi.crud.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EmployeeCreateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotNull(message = "Id do departamento é obrigatório")
        Long departmentId,

        @NotNull(message = "Dados do cartão são obrigatórios")
        @Valid
        CardCreateDTO card
) {
}
