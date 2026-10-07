package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.DepartmentCreateDTO;
import com.jotaefi.crud.dto.request.DepartmentUpdateDTO;
import com.jotaefi.crud.dto.response.DepartmentResponseDTO;
import com.jotaefi.crud.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody @Valid DepartmentCreateDTO departmentCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<DepartmentResponseDTO> findAllDepartments() {
        return departmentService.findAllDepartments();
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponseDTO updateDepartment(@RequestBody DepartmentUpdateDTO departmentUpdateDTO, @PathVariable Long departmentId) {
        return departmentService.updateDepartment(departmentUpdateDTO, departmentId);
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
    }
}
