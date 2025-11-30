package com.example.movie_catalog.Repository;

import com.example.movie_catalog.Entity.CalificacionEntity;
import com.example.movie_catalog.Entity.PeliculaEntity;
import com.example.movie_catalog.Entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalificacionRepository extends JpaRepository<CalificacionEntity, Integer> {
    Optional<CalificacionEntity> findByUsuarioCalificadorAndPelicula(UsuarioEntity usuario, PeliculaEntity pelicula);
    @Query("SELECT c FROM CalificacionEntity c WHERE c.usuarioCalificador.usuarioId = :usuarioId AND c.estado = true")
    List<CalificacionEntity> findByUsuarioId(@Param("usuarioId") int usuarioId);
    @Query("SELECT c FROM CalificacionEntity c WHERE c.usuarioCalificador = :usuario AND c.estado = true")
    Page<CalificacionEntity> findByUsuarioCalificadorAndEstadoTrue(@Param("usuario") UsuarioEntity usuario, Pageable pageable);
    @Query("SELECT AVG(c.calificacionValor) FROM CalificacionEntity c WHERE c.pelicula.peliculaId = :peliculaId AND c.estado = true")
    Double calcularPromedioPorPelicula(@Param("peliculaId") Integer peliculaId);  // Integer
    @Query("SELECT COUNT(c) FROM CalificacionEntity c WHERE c.pelicula.peliculaId = :peliculaId AND c.estado = true")
    Long contarCalificacionesPorPelicula(@Param("peliculaId") Integer peliculaId);  // Integer
}