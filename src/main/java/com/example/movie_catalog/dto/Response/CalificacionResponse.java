package com.example.movie_catalog.dto.Response;

import java.util.Date;

public class CalificacionResponse {
    private int calificacionId;
    private String peliculaNombre;
    private int calificacionValor;
    private Date createdAt;

    public CalificacionResponse(int calificacionId, String peliculaNombre, int calificacionValor, Date createdAt) {
        this.calificacionId = calificacionId;
        this.peliculaNombre = peliculaNombre;
        this.calificacionValor = calificacionValor;
        this.createdAt = createdAt;
    }

    public int getCalificacionId() {
        return calificacionId;
    }

    public void setCalificacionId(int calificacionId) {
        this.calificacionId = calificacionId;
    }

    public String getPeliculaNombre() {
        return peliculaNombre;
    }

    public void setPeliculaNombre(String peliculaNombre) {
        this.peliculaNombre = peliculaNombre;
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
}
