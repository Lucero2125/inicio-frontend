package com.example.movie_catalog.ServiceImpl;

import com.example.movie_catalog.Service.CalificacionService;
import com.example.movie_catalog.dto.Request.CalificacionRequest;
import com.example.movie_catalog.dto.Response.CalificacionResponse;
import com.example.movie_catalog.Entity.CalificacionEntity;
import com.example.movie_catalog.Entity.PeliculaEntity;
import com.example.movie_catalog.Entity.UsuarioEntity;
import com.example.movie_catalog.Repository.CalificacionRepository;
import com.example.movie_catalog.Repository.PeliculaRepository;
import com.example.movie_catalog.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public CalificacionResponse calificarPelicula(CalificacionRequest request) {
        if (request.getCalificacionValor() < 1 || request.getCalificacionValor() > 5) {
            return null;
        }
        Integer peliculaId;
        try {
            peliculaId = Integer.parseInt(request.getPeliculaId());  // String a Integer
        } catch (NumberFormatException e) {
            return null;  // ID inválido
        }
        Optional<PeliculaEntity> optionalPelicula = peliculaRepository.findById(peliculaId);
        if (optionalPelicula.isEmpty()) {
            return null;
        }
        PeliculaEntity pelicula = optionalPelicula.get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findByUsuarioEmail(email);
        if (optionalUsuario.isEmpty()) {
            return null;
        }
        UsuarioEntity usuario = optionalUsuario.get();
        Optional<CalificacionEntity> existing = calificacionRepository.findByUsuarioCalificadorAndPelicula(usuario, pelicula);
        CalificacionEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setCalificacionValor(request.getCalificacionValor());
            entity.setCreatedAt(new Date());
        } else {
            entity = new CalificacionEntity();
            entity.setCalificacionValor(request.getCalificacionValor());
            entity.setPelicula(pelicula);
            entity.setUsuarioCalificador(usuario);
            entity.setCreatedAt(new Date());
            entity.setEstado(true);
        }
        calificacionRepository.save(entity);


        Double promedio = calificacionRepository.calcularPromedioPorPelicula(peliculaId);
        Long count = calificacionRepository.contarCalificacionesPorPelicula(peliculaId);
        if (promedio != null && count > 0) {
            pelicula.setPeliculaRatingPromedio(promedio);
        } else {
            pelicula.setPeliculaRatingPromedio(0.0);
        }
        peliculaRepository.save(pelicula);

        return new CalificacionResponse(entity.getCalificacionId(), pelicula.getPeliculaNombre(),
                entity.getCalificacionValor(), entity.getCreatedAt());
    }

    @Override
    public boolean eliminarCalificacion(Integer peliculaId) {  // Integer
        Optional<PeliculaEntity> optionalPelicula = peliculaRepository.findById(peliculaId);
        if (optionalPelicula.isEmpty()) {
            return false;
        }
        PeliculaEntity pelicula = optionalPelicula.get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findByUsuarioEmail(email);
        if (optionalUsuario.isEmpty()) {
            return false;
        }
        UsuarioEntity usuario = optionalUsuario.get();
        Optional<CalificacionEntity> optional = calificacionRepository.findByUsuarioCalificadorAndPelicula(usuario, pelicula);
        if (optional.isEmpty()) {
            return false;
        }
        CalificacionEntity entity = optional.get();
        entity.setEstado(false);
        calificacionRepository.save(entity);

        // Recalcular promedio
        Double promedio = calificacionRepository.calcularPromedioPorPelicula(peliculaId);
        Long count = calificacionRepository.contarCalificacionesPorPelicula(peliculaId);
        if (promedio != null && count > 0) {
            pelicula.setPeliculaRatingPromedio(promedio);
        } else {
            pelicula.setPeliculaRatingPromedio(0.0);
        }
        peliculaRepository.save(pelicula);
        return true;
    }

    @Override
    public Page<CalificacionResponse> obtenerMisCalificaciones(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findByUsuarioEmail(email);
        if (optionalUsuario.isEmpty()) {
            return Page.empty();
        }
        UsuarioEntity usuario = optionalUsuario.get();
        Page<CalificacionEntity> page = calificacionRepository.findByUsuarioCalificadorAndEstadoTrue(usuario, pageable);
        return page.map(entity -> new CalificacionResponse(entity.getCalificacionId(),
                entity.getPelicula().getPeliculaNombre(), entity.getCalificacionValor(), entity.getCreatedAt()));
    }
}
