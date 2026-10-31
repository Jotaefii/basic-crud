package com.jotaefi.crud.dto.response;

import lombok.Builder;

@Builder
public record DepartmentResponseDTO(
        Long id,
        String name
) {
}
