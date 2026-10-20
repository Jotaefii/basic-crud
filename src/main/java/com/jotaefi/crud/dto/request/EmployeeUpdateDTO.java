package com.jotaefi.crud.dto.request;

public record EmployeeUpdateDTO(
        String name,
        String email,
        Long departmentId
) {
}
