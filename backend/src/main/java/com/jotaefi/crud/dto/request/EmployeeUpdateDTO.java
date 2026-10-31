package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.Pattern;

public record EmployeeUpdateDTO(
        @Pattern(regexp = ".*\\S.*", message = "Nome é obrigatorio")
        String name,

        @Pattern(regexp = ".*\\S.*", message = "Email é obrigatorio")
        String email,

        @Pattern(regexp = ".*\\S.*", message = "Id do departamento é obrigatorio")
        Long departmentId
) {
}
