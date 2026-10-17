package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.EmployeeCreateDTO;
import com.jotaefi.crud.dto.request.EmployeeUpdateDTO;
import com.jotaefi.crud.dto.response.EmployeeResponseDTO;
import com.jotaefi.crud.enums.UserStatus;
import com.jotaefi.crud.service.EmployeeService;
import com.jotaefi.crud.service.EmployeeUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
@Tag(name = "Funcionários", description = "Gerenciamento de funcionários")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeUpdateService employeeUpdateService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar funcionário", description = "Cria um novo funcionário na empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador.")
    })
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar funcionários", description = "Retorna todos os funcionários ativos/férias cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador.")
    })
    public ResponseEntity<Page<EmployeeResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar funcionários por departamento", description = "Retorna todos funcionários ativos/férias de um departamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado.")
    })
    public ResponseEntity<Page<EmployeeResponseDTO>> findByDepartmentId(@PathVariable Long departmentId, Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByDepartmentId(departmentId, pageable));
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar funcionário", description = "Atualiza os dados de um funcionário pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado.")
    })
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody @Valid EmployeeUpdateDTO employeeUpdateDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeUpdateService.updateAndGetResponse(employeeUpdateDTO, employeeId));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar funcionários por status", description = "Retorna todos os funcionários de acordo com seu status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
    })
    public ResponseEntity<List<EmployeeResponseDTO>> findAllByStatus(@RequestParam String status) {
        UserStatus userStatus = UserStatus.fromString(status);
        return ResponseEntity.ok(employeeService.findAllByStatus(userStatus));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar funcionários por nome", description = "Retorna os funcionários por nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
    })
    public ResponseEntity<List<EmployeeResponseDTO>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findByName(name));
    }
}
