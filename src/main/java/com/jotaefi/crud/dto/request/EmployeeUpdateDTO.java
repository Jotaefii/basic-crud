package com.jotaefi.crud.dto.request;

import com.jotaefi.crud.enums.UserStatus;

import java.math.BigDecimal;

public record EmployeeUpdateDTO(
        String name,
        String email,
        BigDecimal salary,
        UserStatus status,
        Long departmentId
) {
}
