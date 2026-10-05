package com.jotaefi.crud.service;

import com.jotaefi.crud.config.TokenProvider;
import com.jotaefi.crud.dto.request.LoginRequestDTO;
import com.jotaefi.crud.dto.response.TokenResponseDTO;
import com.jotaefi.crud.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatorService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    @Value("${jwt.expiration}")
    private long expiration;

    public TokenResponseDTO login(LoginRequestDTO request) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            String token = tokenProvider.generateToken(auth);

            return new TokenResponseDTO(token, expiration);
        }
        catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais inválidas");
        }
        catch (Exception e) {
            throw e;
        }
    }
}
