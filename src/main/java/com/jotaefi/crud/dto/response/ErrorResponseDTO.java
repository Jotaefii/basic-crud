package com.jotaefi.crud.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponseDTO(
        List<String> message,
        Integer status
) {
}
