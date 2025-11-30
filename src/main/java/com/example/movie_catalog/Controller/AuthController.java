package com.example.movie_catalog.Controller;

import com.example.movie_catalog.dto.Request.LoginRequest;
import com.example.movie_catalog.dto.Request.UsuarioCreateRequest;
import com.example.movie_catalog.dto.ResponseBase;
import com.example.movie_catalog.Service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticación", description = "Endpoints para login y register")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login de usuario (público)")
    @PostMapping("/login")
    public ResponseEntity<ResponseBase<String>> login(@RequestBody LoginRequest request) {
        ResponseBase<String> response = authService.login(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @Operation(summary = "Register nuevo usuario (solo admin)")
    @PostMapping("/register")
    public ResponseEntity<ResponseBase<Void>> register(@RequestBody UsuarioCreateRequest request) {
        ResponseBase<Void> response = authService.register(request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }
}


