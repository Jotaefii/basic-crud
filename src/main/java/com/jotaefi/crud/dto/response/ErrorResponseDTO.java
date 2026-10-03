package com.jotaefi.crud.dto.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponseDTO(
        String message,
        Integer status,
        Map<String, String> errors
) {
}
