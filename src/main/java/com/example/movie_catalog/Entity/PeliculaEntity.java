package com.example.movie_catalog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name = "pelicula")
@Getter
@Setter
public class PeliculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pelicula_id")
    private Integer peliculaId;
    @Column(name = "pelicula_nombre")
    private String peliculaNombre;

    @Column(name = "pelicula_anio")
    private int peliculaAnio;

    @Column(name = "pelicula_sinopsis", length = 1000)
    private String peliculaSinopsis;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoriaEntity;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuarioCreador;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    private boolean estado = true;

    @Column(name = "pelicula_rating_promedio")
    private double peliculaRatingPromedio = 0.0;

    public PeliculaEntity() {
    }

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getPeliculaNombre() {
        return peliculaNombre;
    }

    public void setPeliculaNombre(String peliculaNombre) {
        this.peliculaNombre = peliculaNombre;
    }

    public int getPeliculaAnio() {
        return peliculaAnio;
    }

    public void setPeliculaAnio(int peliculaAnio) {
        this.peliculaAnio = peliculaAnio;
    }

    public String getPeliculaSinopsis() {
        return peliculaSinopsis;
    }

    public void setPeliculaSinopsis(String peliculaSinopsis) {
        this.peliculaSinopsis = peliculaSinopsis;
    }

    public CategoriaEntity getCategoriaEntity() {
        return categoriaEntity;
    }

    public void setCategoriaEntity(CategoriaEntity categoriaEntity) {
        this.categoriaEntity = categoriaEntity;
    }

    public UsuarioEntity getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(UsuarioEntity usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPeliculaRatingPromedio() {
        return peliculaRatingPromedio;
    }

    public void setPeliculaRatingPromedio(double peliculaRatingPromedio) {
        this.peliculaRatingPromedio = peliculaRatingPromedio;
    }
}

