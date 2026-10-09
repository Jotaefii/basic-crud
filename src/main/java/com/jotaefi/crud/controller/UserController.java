package com.jotaefi.crud.controller;

import com.jotaefi.crud.dto.response.AdminProfileResponseDTO;
import com.jotaefi.crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AdminProfileResponseDTO> getMyProfile(Authentication authentication) {
        var profile = userService.getMyProfile(authentication.getName());
        return ResponseEntity.ok(profile);
    }
}
