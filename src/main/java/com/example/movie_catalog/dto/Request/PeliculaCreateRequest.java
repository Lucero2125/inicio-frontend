package com.example.movie_catalog.dto.Request;


public class PeliculaCreateRequest {
    private String peliculaNombre;
    private int peliculaAnio;
    private String peliculaSinopsis;
    private String categoriaId;

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

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }
}
