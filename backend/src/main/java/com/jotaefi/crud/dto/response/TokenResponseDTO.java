package com.jotaefi.crud.dto.response;

public record TokenResponseDTO(
        String token,
        long expiration
) {
}
