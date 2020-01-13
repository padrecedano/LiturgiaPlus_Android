package org.deiverbum.app.model;

public class Homilia {
    public String padre;
    public int id;
    public String texto;
    public String tema;
    public String obra;
    public String fecha;

    public Homilia(String padre, int id, String texto, String tema, String obra, String fecha) {
        this.padre = padre;
        this.id = id;
        this.texto = texto;
        this.tema = tema;
        this.obra = obra;
        this.fecha = fecha;

    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }
    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public int getId_homilia() {
        return id;
    }

    public void setId_homilia(int id) {
        this.id = id;
    }

    public String getHomilia() {
        return texto;
    }

    public void setHomilia(String texto) {
        this.texto = texto;
    }
}
