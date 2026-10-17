package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.DepartmentCreateDTO;
import com.jotaefi.crud.dto.request.DepartmentUpdateDTO;
import com.jotaefi.crud.dto.response.DepartmentResponseDTO;
import com.jotaefi.crud.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Departamentos", description = "Gerenciamento de departamentos")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar departamento", description = "Cria um novo departamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador")
    })
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody @Valid DepartmentCreateDTO departmentCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(departmentCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar departamentos", description = "Retorna todos os departamentos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamentos retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador.")
    })
    public ResponseEntity<List<DepartmentResponseDTO>> findAllDepartments() {
        return ResponseEntity.ok(departmentService.findAllDepartments());
    }

    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Editar departamento", description = "Atualiza os dados de um departamento pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento editado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado.")
    })
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@RequestBody @Valid DepartmentUpdateDTO departmentUpdateDTO, @PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentUpdateDTO, departmentId));
    }

    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir departamento", description = "Exclui departamento pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Departamento excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado.")
    })
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }
}
