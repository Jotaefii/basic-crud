package com.jotaefi.crud.dto.response;

import java.math.BigDecimal;

public record CardResponseDTO(
        Long cardId,
        String nameEmployee,
        BigDecimal salary,
        BigDecimal foodAid,
        BigDecimal transportationAid,
        BigDecimal healthAssistence
) {
}
