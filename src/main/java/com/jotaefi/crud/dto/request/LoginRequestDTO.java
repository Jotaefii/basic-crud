package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Email(message = "Email inválido")
        @NotBlank(message = "Email não pode ser vazio")
        String email,

        @NotBlank(message = "Senha não pode ser vazia")
        @Min(value = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password
) {
}
