package com.jotaefi.crud.dto.response;

import java.time.LocalDateTime;

public record AdminProfileResponseDTO(
        Long id,
        String name,
        String email,
        LocalDateTime registrationDate,
        String timeAtCompany
) {
}
