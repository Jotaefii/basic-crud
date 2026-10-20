package com.jotaefi.crud.dto.response;

import java.math.BigDecimal;

public record CardSummaryDTO(
        Long id,
        BigDecimal salary,
        BigDecimal foodAid,
        BigDecimal transportationAid,
        BigDecimal healthAssistence
) {
}
