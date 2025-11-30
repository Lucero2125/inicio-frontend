package com.example.movie_catalog.ServiceImpl;

import com.example.movie_catalog.Service.AuthService;
import com.example.movie_catalog.dto.Request.LoginRequest;
import com.example.movie_catalog.dto.Request.UsuarioCreateRequest;
import com.example.movie_catalog.dto.ResponseBase;
import com.example.movie_catalog.Entity.RoleEntity;
import com.example.movie_catalog.Entity.UsuarioEntity;
import com.example.movie_catalog.Repository.RoleRepository;
import com.example.movie_catalog.Repository.UsuarioRepository;
import com.example.movie_catalog.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;  // Para register

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseBase<String> login(LoginRequest request) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsuarioEmail(),
                            request.getUsuarioPassword()
                    )
            );


            String token = jwtUtil.generateToken(authentication);

            return new ResponseBase<>("Login exitoso", 200, token);
        } catch (BadCredentialsException e) {
            return new ResponseBase<>("Credenciales inválidas", 401);
        } catch (AuthenticationException e) {
            return new ResponseBase<>("Error de autenticación", 401);
        }
    }

    @Override
    public ResponseBase<Void> register(UsuarioCreateRequest request) {
        if (usuarioRepository.findByUsuarioEmail(request.getUsuarioEmail()).isPresent()) {
            return new ResponseBase<>("Email ya existe", 400);
        }

        RoleEntity role = roleRepository.findByRolNombre(request.getRolNombre())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRolNombre()));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsuarioEmail(request.getUsuarioEmail());
        usuario.setUsuarioPassword(passwordEncoder.encode(request.getUsuarioPassword()));  // Hashea
        usuario.setRoleEntity(role);
        usuario.setCreatedAt(new Date());
        usuario.setEstado(true);

        usuarioRepository.save(usuario);

        return new ResponseBase<>("Usuario registrado exitosamente", 201);
    }
}

