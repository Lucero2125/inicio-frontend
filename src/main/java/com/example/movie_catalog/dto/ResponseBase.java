package com.example.movie_catalog.dto;


public class ResponseBase<T> {
    private String mensaje;
    private int codigo;
    private T data;

    public ResponseBase() {
    }

    public ResponseBase(String mensaje, int codigo, T data) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.data = data;
    }

    public ResponseBase(String mensaje, int codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
        this.data = null;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

