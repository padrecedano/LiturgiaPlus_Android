package org.deiverbum.app.model;

public class Homilia {
    public String padre;
    public int id;
    public String texto;

    public Homilia(String padre, int id, String texto) {
        this.padre = padre;
        this.id = id;
        this.texto = texto;
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
