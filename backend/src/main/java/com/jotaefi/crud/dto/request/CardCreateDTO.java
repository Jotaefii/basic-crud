package com.jotaefi.crud.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CardCreateDTO(
        @NotNull(message = "Salário é obrigatório")
        @PositiveOrZero
        BigDecimal salary,

        @NotNull(message = "Valor do benefício alimentação é obrigatório")
        @PositiveOrZero
        BigDecimal food,

        @NotNull(message = "Valor do benefício transporte é obrigatório")
        @PositiveOrZero
        BigDecimal transport,

        @NotNull(message = "Valor do benefício saúde é obrigatório")
        @PositiveOrZero
        BigDecimal health
) {
}
