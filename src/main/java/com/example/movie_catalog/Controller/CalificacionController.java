package com.example.movie_catalog.Controller;

import com.example.movie_catalog.dto.Request.CalificacionRequest;
import com.example.movie_catalog.dto.Response.CalificacionResponse;
import com.example.movie_catalog.dto.ResponseBase;
import com.example.movie_catalog.Service.CalificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @Operation(summary = "Calificar película (autenticado)")
    @PostMapping("/rate")
    public ResponseBase<CalificacionResponse> calificarPelicula(@RequestBody CalificacionRequest request) {
        ResponseBase<CalificacionResponse> response = new ResponseBase<>();
        CalificacionResponse result = calificacionService.calificarPelicula(request);
        if (result != null) {
            response.setMensaje("Calificación guardada exitosamente");
            response.setCodigo(200);
            response.setData(result);
        } else {
            response.setMensaje("Error al calificar (película no encontrada, valor inválido o no autenticado)");
            response.setCodigo(400);
            response.setData(null);
        }
        return response;
    }
    @Operation(summary = "Eliminar calificación (autenticado)")
    @DeleteMapping("/remove/{peliculaId}")
    public ResponseBase<Void> eliminarCalificacion(@PathVariable String peliculaIdStr) {
        ResponseBase<Void> response = new ResponseBase<>();
        Integer peliculaId;
        try {
            peliculaId = Integer.parseInt(peliculaIdStr);
        } catch (NumberFormatException e) {response.setMensaje("ID de película inválido (debe ser número)");
            response.setCodigo(400);
            response.setData(null);
            return response;
        }
        boolean success = calificacionService.eliminarCalificacion(peliculaId);
        if (success) {
            response.setMensaje("Calificación eliminada exitosamente");
            response.setCodigo(200);
            response.setData(null);
        } else {
            response.setMensaje("Calificación no encontrada o película no existe");
            response.setCodigo(404);
            response.setData(null);
        }
        return response;
    }
    @Operation(summary = "Obtener mis calificaciones (autenticado)")
    @GetMapping("/my-ratings")
    public ResponseBase<Page<CalificacionResponse>> obtenerMisCalificaciones(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        ResponseBase<Page<CalificacionResponse>> response = new ResponseBase<>();
        String[] sortParts = sort.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParts[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));
        Page<CalificacionResponse> result = calificacionService.obtenerMisCalificaciones(pageable);
        response.setMensaje("Calificaciones encontradas");
        response.setCodigo(200);
        response.setData(result);
        return response;
    }
}