package com.jotaefi.crud.dto.response;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(
        String message,
        Integer status
) {
}
