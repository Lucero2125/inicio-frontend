package com.example.movie_catalog.Controller;

import com.example.movie_catalog.dto.Request.PeliculaCreateRequest;
import com.example.movie_catalog.dto.Request.PeliculaUpdateRequest;
import com.example.movie_catalog.dto.Response.PeliculaResponse;
import com.example.movie_catalog.dto.ResponseBase;
import com.example.movie_catalog.Service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Películas", description = "Endpoints para gestionar películas")
@RestController
@RequestMapping("/api/v1/pelicula")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Operation(summary = "Guardar película (solo admin)")
    @PostMapping("/save")
    public ResponseEntity<ResponseBase<PeliculaResponse>> guardarPelicula(@RequestBody PeliculaCreateRequest request) {
        ResponseBase<PeliculaResponse> response = peliculaService.guardarPelicula(request);  // Service maneja ResponseBase
        return ResponseEntity.status(response.getCodigo()).body(response);  // 201 o 400
    }

    @Operation(summary = "Actualizar película (solo admin)")
    @PutMapping("/update/{peliculaId}")
    public ResponseEntity<ResponseBase<PeliculaResponse>> actualizarPelicula(@PathVariable String peliculaIdStr, @RequestBody PeliculaUpdateRequest request) {
        Integer peliculaId;
        try {
            peliculaId = Integer.parseInt(peliculaIdStr);
        } catch (NumberFormatException e) {
            ResponseBase<PeliculaResponse> errorResponse = new ResponseBase<>("ID de película inválido (debe ser número)", 400);
            return ResponseEntity.status(400).body(errorResponse);
        }
        ResponseBase<PeliculaResponse> response = peliculaService.actualizarPelicula(peliculaId, request);
        return ResponseEntity.status(response.getCodigo()).body(response);
    }

    @Operation(summary = "Eliminar película (solo admin)")
    @DeleteMapping("/delete/{peliculaId}")
    public ResponseEntity<ResponseBase<Void>> eliminarPelicula(@PathVariable String peliculaIdStr) {
        Integer peliculaId;
        try {
            peliculaId = Integer.parseInt(peliculaIdStr);
        } catch (NumberFormatException e) {
            ResponseBase<Void> errorResponse = new ResponseBase<>("ID de película inválido (debe ser número)", 400);
            return ResponseEntity.status(400).body(errorResponse);
        }
        ResponseBase<Void> response = peliculaService.eliminarPelicula(peliculaId);
        return ResponseEntity.status(response.getCodigo()).body(response);  // 200 o 404
    }

    @Operation(summary = "Obtener película por ID (público)")
    @GetMapping("/find/{peliculaId}")
    public ResponseEntity<ResponseBase<PeliculaResponse>> obtenerPeliculaPorId(@PathVariable String peliculaIdStr) {
        Integer peliculaId;
        try {
            peliculaId = Integer.parseInt(peliculaIdStr);
        } catch (NumberFormatException e) {
            ResponseBase<PeliculaResponse> errorResponse = new ResponseBase<>("ID de película inválido (debe ser número)", 400);
            return ResponseEntity.status(400).body(errorResponse);
        }
        ResponseBase<PeliculaResponse> response = peliculaService.obtenerPeliculaPorId(peliculaId);
        return ResponseEntity.status(response.getCodigo()).body(response);  // 200 o 404
    }

    @Operation(summary = "Obtener películas paginadas (buscar/filtrar/ordenar, público)")
    @GetMapping("/all")
    public ResponseEntity<ResponseBase<Page<PeliculaResponse>>> obtenerPeliculasPaginadas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer categoriaId,
            @RequestParam(required = false) Integer anio,
            @Parameter(description = "Página (default 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño por página (default 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Ordenar por: peliculaAnio, peliculaNombre, createdAt, peliculaRatingPromedio (asc/desc)") @RequestParam(defaultValue = "peliculaAnio,asc") String sort) {
        ResponseBase<Page<PeliculaResponse>> response = peliculaService.obtenerPeliculasPaginadas(Optional.ofNullable(nombre).orElse(null), categoriaId, anio, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0])));
        return ResponseEntity.ok(response);  // Siempre 200 (service maneja errores)
    }
}
