package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.dto.response.EmployeeProfileResponseDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.enums.UserStatus;
import com.jotaefi.crud.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeResponseDTO> findAll(Pageable pageable) {
        return employeeService.findAll(pageable);
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDTO findById(@PathVariable Long employeeId) {
        return employeeService.findEmployeeById(employeeId);
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponseDTO> findByDepartmentId(@PathVariable Long departmentId) {
        return employeeService.findByDepartmentId(departmentId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDTO updateEmployee(@RequestBody @Valid EmployeeUpdateDTO employeeUpdateDTO, @PathVariable Long employeeId) {
        return employeeService.updateEmployee(employeeUpdateDTO, employeeId);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeResponseDTO>> findAllByStatus(@PathVariable String status) {
        UserStatus userStatus = UserStatus.fromString(status);
        return ResponseEntity.ok(employeeService.findAllByStatus(userStatus));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EmployeeResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByName(name));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE, ROLE_ADMIN')")
    public ResponseEntity<EmployeeProfileResponseDTO> getMyProfile(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getMyProfile(authentication.getName()));
    }
}
