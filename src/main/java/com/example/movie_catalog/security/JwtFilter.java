package com.example.movie_catalog.security;

import com.example.movie_catalog.ServiceImpl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtFilter: Entrando en doFilterInternal para path: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        System.out.println("JwtFilter: AuthHeader recibido: " + (authHeader != null ? authHeader.substring(0, Math.min(50, authHeader.length())) + "..." : "NULL"));  // Log header (truncado por seguridad)

        String username = null;
        String jwt = null;
        String rol = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            System.out.println("JwtFilter: Token extraído (longitud): " + jwt.length());
            username = jwtUtil.extractUsername(jwt);
            System.out.println("JwtFilter: Username extraído: " + username);
            rol = jwtUtil.extractRol(jwt);
            System.out.println("JwtFilter: Rol extraído del token: " + rol);
        } else {
            System.out.println("JwtFilter: No hay header Bearer válido");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("JwtFilter: Cargando UserDetails para " + username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("JwtFilter: UserDetails loaded, authorities: " + userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                System.out.println("JwtFilter: Token válido, creando authToken");
                List<SimpleGrantedAuthority> authorities;
                if (rol != null && !rol.isEmpty()) {
                    authorities = List.of(new SimpleGrantedAuthority(rol));
                    System.out.println("JwtFilter: Usando rol del token: " + rol);
                } else {
                    authorities = userDetails.getAuthorities().stream()
                            .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                            .collect(Collectors.toList());
                    System.out.println("JwtFilter: Usando authorities de UserDetails: " + authorities);
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("JwtFilter: Authentication seteada con authorities: " + authorities);
            } else {
                System.out.println("JwtFilter: Token inválido para " + username);
            }
        } else {
            System.out.println("JwtFilter: No se procesa (username null o ya autenticado)");
        }

        filterChain.doFilter(request, response);
        System.out.println("JwtFilter: Saliendo de doFilterInternal");
    }
}

