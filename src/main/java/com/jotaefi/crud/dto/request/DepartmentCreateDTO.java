package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DepartmentCreateDTO(
        @NotBlank
        String name
) {
}
