package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CardUpdateDTO(
        @PositiveOrZero(message = "Salário não pode ser negativo")
        BigDecimal salary,
        @PositiveOrZero(message = "Benefício alimentação não pode ser negativo")
        BigDecimal food,
        @PositiveOrZero(message = "Benefício transporte não pode ser negativo")
        BigDecimal transport,
        @PositiveOrZero(message = "Benefício saúde não pode ser negativo")
        BigDecimal health
) {
}
