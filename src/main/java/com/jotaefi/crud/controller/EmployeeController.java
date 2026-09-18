package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.enums.EmployeeStatus;
import com.jotaefi.crud.exception.InvalidStatusException;
import com.jotaefi.crud.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO createEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
        return employeeService.createEmployee(employeeCreateDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeResponseDTO> findAll(Pageable pageable) {
        return employeeService.findAll(pageable);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDTO findById(@PathVariable Long employeeId) {
        return employeeService.findEmployeeById(employeeId);
    }

    @GetMapping("/department/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDTO> findByDepartmentId(@PathVariable Long departmentId) {
        return employeeService.findByDepartmentId(departmentId);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDTO updateEmployee(@RequestBody @Valid EmployeeUpdateDTO employeeUpdateDTO, @PathVariable Long employeeId) {
        return employeeService.updateEmployee(employeeUpdateDTO, employeeId);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EmployeeResponseDTO>> findAllByStatus(@PathVariable String status) {
        EmployeeStatus employeeStatus;
        try {
            employeeStatus = EmployeeStatus.valueOf(status.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Status '" + status + "' não existe");
        }
        return ResponseEntity.ok(employeeService.findAllByStatus(employeeStatus));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByName(name));
    }
}
