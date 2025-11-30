package com.example.movie_catalog.Repository;

import com.example.movie_catalog.Entity.PeliculaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<PeliculaEntity, Integer> {

    Optional<PeliculaEntity> findByPeliculaNombre(String peliculaNombre);

    @Query("SELECT p FROM PeliculaEntity p WHERE (:nombre IS NULL OR LOWER(p.peliculaNombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:categoriaId IS NULL OR p.categoriaEntity.categoriaId = :categoriaId) " +
            "AND (:anio IS NULL OR p.peliculaAnio = :anio) AND p.estado = true")
    Page<PeliculaEntity> buscarPeliculasPaginadas(@Param("nombre") String nombre, @Param("categoriaId") Integer categoriaId,
                                                  @Param("anio") Integer anio, Pageable pageable);
}
