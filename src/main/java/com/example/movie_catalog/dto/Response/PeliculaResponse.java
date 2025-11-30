package com.example.movie_catalog.dto.Response;

import java.util.Date;

public class PeliculaResponse {
    private Integer peliculaId;
    private String peliculaNombre;
    private int peliculaAnio;
    private String peliculaSinopsis;
    private String categoriaNombre;
    private String usuarioCreadorEmail;
    private double peliculaRatingPromedio;
    private Date createdAt;

    public PeliculaResponse(Integer peliculaId, String peliculaNombre, int peliculaAnio, String peliculaSinopsis, String categoriaNombre, String usuarioCreadorEmail, double peliculaRatingPromedio, Date createdAt) {
        this.peliculaId = peliculaId;
        this.peliculaNombre = peliculaNombre;
        this.peliculaAnio = peliculaAnio;
        this.peliculaSinopsis = peliculaSinopsis;
        this.categoriaNombre = categoriaNombre;
        this.usuarioCreadorEmail = usuarioCreadorEmail;
        this.peliculaRatingPromedio = peliculaRatingPromedio;
        this.createdAt = createdAt;
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

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getUsuarioCreadorEmail() {
        return usuarioCreadorEmail;
    }

    public void setUsuarioCreadorEmail(String usuarioCreadorEmail) {
        this.usuarioCreadorEmail = usuarioCreadorEmail;
    }

    public double getPeliculaRatingPromedio() {
        return peliculaRatingPromedio;
    }

    public void setPeliculaRatingPromedio(double peliculaRatingPromedio) {
        this.peliculaRatingPromedio = peliculaRatingPromedio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCodigo() {
        return 0;
    }
}
