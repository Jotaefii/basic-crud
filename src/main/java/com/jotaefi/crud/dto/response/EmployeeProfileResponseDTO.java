package com.jotaefi.crud.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EmployeeProfileResponseDTO(
        Long id,
        String name,
        String email,
        LocalDateTime registrationDate,
        String timeAtCompany,
        String departmentName,
        String status,
        BigDecimal salary
) {
}
