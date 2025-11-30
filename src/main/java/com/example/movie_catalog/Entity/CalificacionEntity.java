package com.example.movie_catalog.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "calificacion")

public class CalificacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calificacion_id")
    private int calificacionId;
    @Column(name = "calificacion_valor")
    private int calificacionValor;
    @Column(name = "created_at")
    private Date createdAt;
    private boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", referencedColumnName = "pelicula_id")
    private PeliculaEntity pelicula;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioCalificador;

    public int getCalificacionId() {
        return calificacionId;
    }

    public void setCalificacionId(int calificacionId) {
        this.calificacionId = calificacionId;
    }

    public int getCalificacionValor() {
        return calificacionValor;
    }

    public void setCalificacionValor(int calificacionValor) {
        this.calificacionValor = calificacionValor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public PeliculaEntity getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculaEntity pelicula) {
        this.pelicula = pelicula;
    }

    public UsuarioEntity getUsuarioCalificador() {
        return usuarioCalificador;
    }

    public void setUsuarioCalificador(UsuarioEntity usuarioCalificador) {
        this.usuarioCalificador = usuarioCalificador;
    }
}
