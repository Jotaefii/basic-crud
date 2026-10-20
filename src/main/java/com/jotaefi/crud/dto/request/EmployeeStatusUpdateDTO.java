package com.jotaefi.crud.dto.request;

import com.jotaefi.crud.enums.UserStatus;

public record EmployeeStatusUpdateDTO(
        UserStatus status
) {
}
