package com.jotaefi.crud.dto.response;

import com.jotaefi.crud.enums.UserStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmployeeResponseDTO(
        Long id,
        String name,
        String email,
        Long cardId,
        String departmentName,
        UserStatus status,
        LocalDateTime registrationDate
) {
}
