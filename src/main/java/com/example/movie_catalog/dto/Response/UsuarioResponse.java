package com.example.movie_catalog.dto.Response;

public class UsuarioResponse {
    private int usuarioId;
    private String usuarioEmail;
    private String rolNombre;

    public UsuarioResponse(int usuarioId, String usuarioEmail, String rolNombre) {
        this.usuarioId = usuarioId;
        this.usuarioEmail = usuarioEmail;
        this.rolNombre = rolNombre;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
}

