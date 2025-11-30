package com.example.movie_catalog.Service;

public interface JwtService {
    String generarToken(String email, String rol);
    String extraerEmail(String token);
    String extraerRol(String token);
    boolean validarToken(String token, String email);
}

