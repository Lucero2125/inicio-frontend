package com.example.movie_catalog.Repository;

import com.example.movie_catalog.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByUsuarioEmail(String usuarioEmail);
    boolean existsByUsuarioEmail(String usuarioEmail);
}
