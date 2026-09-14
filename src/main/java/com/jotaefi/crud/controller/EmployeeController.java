package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.EmployeeCreateDTO;
import com.jotaefi.crud.dto.EmployeeResponseDTO;
import com.jotaefi.crud.dto.EmployeeUpdateDTO;
import com.jotaefi.crud.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<EmployeeResponseDTO> findAll() {
        return employeeService.findAll();
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

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
