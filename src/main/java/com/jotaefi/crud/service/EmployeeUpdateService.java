package com.jotaefi.crud.service;

import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeUpdateService {

    private final EmployeeService employeeService;

    public EmployeeResponseDTO updateAndGetResponse(EmployeeUpdateDTO request, Long employeeId) {
        employeeService.updateEmployee(request, employeeId);

        return employeeService.findEmployeeById(employeeId);
    }
}
