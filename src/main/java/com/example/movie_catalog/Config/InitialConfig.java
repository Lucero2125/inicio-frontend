package com.example.movie_catalog.Config;

import com.example.movie_catalog.Entity.CategoriaEntity;
import com.example.movie_catalog.Entity.RoleEntity;
import com.example.movie_catalog.Entity.UsuarioEntity;
import com.example.movie_catalog.Repository.CategoriaRepository;
import com.example.movie_catalog.Repository.RoleRepository;
import com.example.movie_catalog.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitialConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @org.springframework.context.annotation.Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (roleRepository.findByRolNombre("ADMIN").isEmpty()) {
                RoleEntity adminRole = new RoleEntity();
                adminRole.setRolNombre("ADMIN");
                roleRepository.save(adminRole);
            }
            if (roleRepository.findByRolNombre("USER").isEmpty()) {
                RoleEntity userRole = new RoleEntity();
                userRole.setRolNombre("USER");
                roleRepository.save(userRole);
            }

            if (categoriaRepository.findByCategoriaNombre("ciencia ficcion").isEmpty()) {
                CategoriaEntity cat1 = new CategoriaEntity();
                cat1.setCategoriaNombre("ciencia ficcion");
                cat1.setCreatedAt(new Date());
                categoriaRepository.save(cat1);
            }
            if (categoriaRepository.findByCategoriaNombre("comedia").isEmpty()) {
                CategoriaEntity cat2 = new CategoriaEntity();
                cat2.setCategoriaNombre("comedia");
                cat2.setCreatedAt(new Date());
                categoriaRepository.save(cat2);
            }

            if (!usuarioRepository.existsByUsuarioEmail("admin@example.com")) {
                UsuarioEntity usuarioEntity = new UsuarioEntity();
                usuarioEntity.setUsuarioEmail("admin@example.com");
                usuarioEntity.setUsuarioPassword(new BCryptPasswordEncoder().encode("admin123"));
                usuarioEntity.setRoleEntity(roleRepository.findByRolNombre("ADMIN").get());
                usuarioEntity.setCreatedAt(new Date());
                usuarioRepository.save(usuarioEntity);
            }
        };
    }
}
