package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.Pattern;

public record DepartmentUpdateDTO(
        @Pattern(regexp = ".*\\S.*", message = "Nome é obrigatorio")
        String name
) {
}
