package com.jotaefi.crud.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EmployeeResponseDTO(
        Long id,
        String name,
        String email,
        BigDecimal salary,
        String departmentName
) {
}
