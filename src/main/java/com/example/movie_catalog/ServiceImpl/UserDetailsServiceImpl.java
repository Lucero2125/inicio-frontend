package com.example.movie_catalog.ServiceImpl;

import com.example.movie_catalog.Entity.UsuarioEntity;
import com.example.movie_catalog.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new CustomUserDetails(usuario);
    }

    public static class CustomUserDetails implements UserDetails {
        private final UsuarioEntity usuario;

        public CustomUserDetails(UsuarioEntity usuario) {
            this.usuario = usuario;
        }

        @Override
        public String getPassword() {
            return usuario.getUsuarioPassword();
        }

        @Override
        public String getUsername() {
            return usuario.getUsuarioEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return usuario.isEstado();
        }

        @Override
        public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
            return new ArrayList<>() {{
                add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + usuario.getRoleEntity().getRolNombre()));
            }};
        }

        public UsuarioEntity getUsuario() {
            return usuario;
        }
    }
}
