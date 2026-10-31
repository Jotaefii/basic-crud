package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateDTO(
        @NotBlank(message = "Nome do departamento é obrigatório")
        String name
) {
}
