package com.example.movie_catalog.Service;

import com.example.movie_catalog.dto.Request.PeliculaCreateRequest;
import com.example.movie_catalog.dto.Request.PeliculaUpdateRequest;
import com.example.movie_catalog.dto.Response.PeliculaResponse;
import com.example.movie_catalog.dto.ResponseBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PeliculaService {
    ResponseBase<PeliculaResponse> guardarPelicula(PeliculaCreateRequest request);
    ResponseBase<PeliculaResponse> actualizarPelicula(Integer peliculaId, PeliculaUpdateRequest request);
    ResponseBase<Void> eliminarPelicula(Integer peliculaId);
    ResponseBase<PeliculaResponse> obtenerPeliculaPorId(Integer peliculaId);
    ResponseBase<Page<PeliculaResponse>> obtenerPeliculasPaginadas(String nombre, Integer categoriaId, Integer anio, Pageable pageable);
}