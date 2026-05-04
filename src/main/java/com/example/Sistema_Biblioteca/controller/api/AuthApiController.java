package com.example.Sistema_Biblioteca.controller.api;

import com.example.Sistema_Biblioteca.dto.request.LoginRequest;
import com.example.Sistema_Biblioteca.dto.request.RegisterRequest;
import com.example.Sistema_Biblioteca.dto.response.AuthResponse;
import com.example.Sistema_Biblioteca.service.AuthService;
import com.example.Sistema_Biblioteca.shared.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("Login exitoso", response)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Usuario registrado correctamente", response));
    }
}