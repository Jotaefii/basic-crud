package com.jotaefi.crud.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jotaefi.crud.enums.EmployeeStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record EmployeeResponseDTO(
        Long id,
        String name,
        String email,
        BigDecimal salary,
        String departmentName,
        EmployeeStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime registrationDate
) {
}
