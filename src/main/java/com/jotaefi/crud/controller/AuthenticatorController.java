package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.request.LoginRequestDTO;
import com.jotaefi.crud.dto.response.TokenResponseDTO;
import com.jotaefi.crud.service.AuthenticatorService;
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
public class AuthenticatorController {

     private final AuthenticatorService authenticatorService;

     @PostMapping("/login")
     public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
         return ResponseEntity.status(HttpStatus.OK).body(authenticatorService.login(request));
     }

}
