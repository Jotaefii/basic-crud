package com.jotaefi.crud.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        Integer status
) {
}
