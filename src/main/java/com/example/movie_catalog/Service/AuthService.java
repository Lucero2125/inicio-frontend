package com.example.movie_catalog.Service;

import com.example.movie_catalog.dto.Request.LoginRequest;
import com.example.movie_catalog.dto.Request.UsuarioCreateRequest;
import com.example.movie_catalog.dto.Response.UsuarioResponse;
import com.example.movie_catalog.dto.ResponseBase;


public interface AuthService {
    ResponseBase<String> login(LoginRequest request);
    ResponseBase<Void> register(UsuarioCreateRequest request);
}
