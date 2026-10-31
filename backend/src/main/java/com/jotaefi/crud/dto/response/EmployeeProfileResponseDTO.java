package com.jotaefi.crud.dto.response;

import java.time.LocalDateTime;

public record EmployeeProfileResponseDTO(
        Long id,
        String name,
        String email,
        LocalDateTime registrationDate,
        String timeAtCompany,
        String departmentName,
        String status,
        CardSummaryDTO card
) implements ProfileResponse {
}
