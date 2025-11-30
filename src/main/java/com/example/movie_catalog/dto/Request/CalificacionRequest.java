package com.example.movie_catalog.dto.Request;


public class CalificacionRequest {
    private String peliculaId;
    private int calificacionValor;  // 1-5

    public String getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(String peliculaId) {
        this.peliculaId = peliculaId;
    }

    public int getCalificacionValor() {
        return calificacionValor;
    }

    public void setCalificacionValor(int calificacionValor) {
        this.calificacionValor = calificacionValor;
    }
}
