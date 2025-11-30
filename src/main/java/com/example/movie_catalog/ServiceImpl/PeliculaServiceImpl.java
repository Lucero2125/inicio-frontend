package com.example.movie_catalog.ServiceImpl;

import com.example.movie_catalog.Repository.CalificacionRepository;
import com.example.movie_catalog.Service.PeliculaService;
import com.example.movie_catalog.dto.Request.PeliculaCreateRequest;
import com.example.movie_catalog.dto.Request.PeliculaUpdateRequest;
import com.example.movie_catalog.dto.Response.PeliculaResponse;
import com.example.movie_catalog.Entity.CategoriaEntity;
import com.example.movie_catalog.Entity.PeliculaEntity;
import com.example.movie_catalog.Entity.UsuarioEntity;
import com.example.movie_catalog.Repository.CategoriaRepository;
import com.example.movie_catalog.Repository.PeliculaRepository;
import com.example.movie_catalog.Repository.UsuarioRepository;
import com.example.movie_catalog.dto.ResponseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private CalificacionRepository calificacionRepository;

    @Override
    public ResponseBase<PeliculaResponse> guardarPelicula(PeliculaCreateRequest request) {
        try {
            // Parse y verifica categoría
            Integer categoriaId;
            try {
                categoriaId = Integer.parseInt(request.getCategoriaId());
            } catch (NumberFormatException e) {
                return new ResponseBase<>("Categoría inválida (debe ser número)", 400);
            }

            Optional<CategoriaEntity> optionalCategoria = categoriaRepository.findById(categoriaId);
            if (optionalCategoria.isEmpty()) {
                return new ResponseBase<>("Categoría no encontrada", 400);
            }

            // Verifica nombre duplicado
            if (peliculaRepository.findByPeliculaNombre(request.getPeliculaNombre()).isPresent()) {
                return new ResponseBase<>("Nombre de película ya existe", 400);
            }

            // Usuario actual (admin)
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findByUsuarioEmail(email);
            if (optionalUsuario.isEmpty()) {
                return new ResponseBase<>("Usuario no autenticado", 401);
            }

            PeliculaEntity entity = new PeliculaEntity();
            entity.setPeliculaNombre(request.getPeliculaNombre());
            entity.setPeliculaAnio(request.getPeliculaAnio());
            entity.setPeliculaSinopsis(request.getPeliculaSinopsis());
            entity.setCategoriaEntity(optionalCategoria.get());
            entity.setUsuarioCreador(optionalUsuario.get());
            entity.setCreatedAt(new Date());
            entity.setEstado(true);
            entity.setPeliculaRatingPromedio(0.0);

            entity = peliculaRepository.save(entity);

            PeliculaResponse result = new PeliculaResponse(
                    entity.getPeliculaId(),
                    entity.getPeliculaNombre(),
                    entity.getPeliculaAnio(),
                    entity.getPeliculaSinopsis(),
                    optionalCategoria.get().getCategoriaNombre(),
                    optionalUsuario.get().getUsuarioEmail(),
                    entity.getPeliculaRatingPromedio(),
                    entity.getCreatedAt()
            );

            return new ResponseBase<>("Película guardada exitosamente", 201, result);
        } catch (Exception e) {
            e.printStackTrace();  // Para debug
            return new ResponseBase<>("Error interno al guardar película: " + e.getMessage(), 500);
        }
    }


    @Override
    public ResponseBase<PeliculaResponse> actualizarPelicula(Integer peliculaId, PeliculaUpdateRequest request) {
        try {

            Optional<PeliculaEntity> optionalPelicula = peliculaRepository.findById(peliculaId);
            if (optionalPelicula.isEmpty()) {
                return new ResponseBase<>("Película no encontrada", 404);
            }

            PeliculaEntity entity = optionalPelicula.get();

            Integer categoriaId;
            try {
                categoriaId = Integer.parseInt(request.getCategoriaId());
            } catch (NumberFormatException e) {
                return new ResponseBase<>("Categoría inválida (debe ser número)", 400);
            }

            Optional<CategoriaEntity> optionalCategoria = categoriaRepository.findById(categoriaId);
            if (optionalCategoria.isEmpty()) {
                return new ResponseBase<>("Categoría no encontrada", 400);
            }

            if (!entity.getPeliculaNombre().equals(request.getPeliculaNombre()) &&
                    peliculaRepository.findByPeliculaNombre(request.getPeliculaNombre()).isPresent()) {
                return new ResponseBase<>("Nombre de película ya existe", 400);
            }

            entity.setPeliculaNombre(request.getPeliculaNombre());
            entity.setPeliculaAnio(request.getPeliculaAnio());
            entity.setPeliculaSinopsis(request.getPeliculaSinopsis());
            entity.setCategoriaEntity(optionalCategoria.get());
            entity.setUpdatedAt(new Date());

            entity = peliculaRepository.save(entity);

            PeliculaResponse result = new PeliculaResponse(
                    entity.getPeliculaId(),
                    entity.getPeliculaNombre(),
                    entity.getPeliculaAnio(),
                    entity.getPeliculaSinopsis(),
                    optionalCategoria.get().getCategoriaNombre(),
                    entity.getUsuarioCreador().getUsuarioEmail(),
                    entity.getPeliculaRatingPromedio(),
                    entity.getUpdatedAt()  // Usa updatedAt para mostrar cambio
            );

            return new ResponseBase<>("Película actualizada exitosamente", 200, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase<>("Error interno al actualizar película: " + e.getMessage(), 500);
        }
    }


    @Override
    public ResponseBase<Void> eliminarPelicula(Integer peliculaId) {
        try {
            Optional<PeliculaEntity> optionalPelicula = peliculaRepository.findById(peliculaId);
            if (optionalPelicula.isEmpty()) {
                return new ResponseBase<>("Película no encontrada", 404);
            }
            PeliculaEntity entity = optionalPelicula.get();

            entity.setEstado(false);
            entity.setUpdatedAt(new Date());

            peliculaRepository.save(entity);

            return new ResponseBase<>("Película eliminada exitosamente", 200);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase<>("Error interno al eliminar película: " + e.getMessage(), 500);
        }
    }


    @Override
    public ResponseBase<PeliculaResponse> obtenerPeliculaPorId(Integer peliculaId) {
        try {

            Optional<PeliculaEntity> optionalPelicula = peliculaRepository.findById(peliculaId);
            if (optionalPelicula.isEmpty()) {
                return new ResponseBase<>("Película no encontrada o eliminada", 404);
            }

            PeliculaEntity entity = optionalPelicula.get();


            String categoriaNombre = entity.getCategoriaEntity().getCategoriaNombre();
            String usuarioEmail = entity.getUsuarioCreador().getUsuarioEmail();


            PeliculaResponse result = new PeliculaResponse(
                    entity.getPeliculaId(),
                    entity.getPeliculaNombre(),
                    entity.getPeliculaAnio(),
                    entity.getPeliculaSinopsis(),
                    categoriaNombre,
                    usuarioEmail,
                    entity.getPeliculaRatingPromedio(),
                    entity.getCreatedAt()
            );

            return new ResponseBase<>("Película encontrada", 200, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase<>("Error interno al obtener película: " + e.getMessage(), 500);
        }
    }


    @Override
    public ResponseBase<Page<PeliculaResponse>> obtenerPeliculasPaginadas(String nombre, Integer categoriaId, Integer anio, Pageable pageable) {
        try {

            Page<PeliculaEntity> entityPage = peliculaRepository.buscarPeliculasPaginadas(nombre, categoriaId, anio, pageable);

            Page<PeliculaResponse> responsePage = entityPage.map(entity -> {
                String categoriaNombre = entity.getCategoriaEntity().getCategoriaNombre();
                String usuarioEmail = entity.getUsuarioCreador().getUsuarioEmail();
                return new PeliculaResponse(
                        entity.getPeliculaId(),
                        entity.getPeliculaNombre(),
                        entity.getPeliculaAnio(),
                        entity.getPeliculaSinopsis(),
                        categoriaNombre,
                        usuarioEmail,
                        entity.getPeliculaRatingPromedio(),
                        entity.getCreatedAt()
                );
            });

            return new ResponseBase<>("Películas encontradas (" + responsePage.getTotalElements() + " total)", 200, responsePage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase<>("Error al obtener películas: " + e.getMessage(), 500);
        }
    }

}
