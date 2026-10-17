package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.LoginRequestDTO;
import com.jotaefi.crud.dto.response.TokenResponseDTO;
import com.jotaefi.crud.security.AuthenticatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Autenticação", description = "Endpoints de autenticação")
public class AuthenticatorController {

     private final AuthenticatorService authenticatorService;

     @PostMapping("/login")
     @Operation(summary = "Realizar login", description = "Autentica o usuário usando e-mail e senha")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
             @ApiResponse(responseCode = "400", description = "Credenciais inválidas ou requisição inválida")
     })
     public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
         return ResponseEntity.status(HttpStatus.OK).body(authenticatorService.login(request));
     }

}
