package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.CardUpdateDTO;
import com.jotaefi.crud.dto.response.CardResponseDTO;
import com.jotaefi.crud.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Validated
@Tag(name = "Carteira", description = "Gerenciamento das carteiras dos funcionários.")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Buscar carteira", description = "Retorna carteira do funcionário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteira retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada")
    })
    public ResponseEntity<CardResponseDTO> findCardByEmployeeId(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.findCardByEmployeeId(id));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar carteira", description = "Atualiza os dados da carteira do funcionário com ID especifico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carteira atualizada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão de administrador."),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada")
    })
    public ResponseEntity<CardResponseDTO> updateCard(@RequestBody @Valid CardUpdateDTO request, @PathVariable Long userId) {
        return ResponseEntity.ok(cardService.updateCard(request, userId));
    }
}
