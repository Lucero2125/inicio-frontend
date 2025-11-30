package com.example.movie_catalog.Service;

import com.example.movie_catalog.dto.Request.CalificacionRequest;
import com.example.movie_catalog.dto.Response.CalificacionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CalificacionService {
    CalificacionResponse calificarPelicula(CalificacionRequest request);
    boolean eliminarCalificacion(Integer peliculaId);
    Page<CalificacionResponse> obtenerMisCalificaciones(Pageable pageable);
}
