package org.deiverbum.app.model;

import java.util.List;

public class Misterio {
    public int id;
    public String titulo;
    public List<String> contenido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getContenidoAll(int dayCode) {
        return contenido;
    }

    public List<String> getContenido() {
        return contenido;
    }

    public void setContenido(List<String> contenido) {
        this.contenido = contenido;
    }
}