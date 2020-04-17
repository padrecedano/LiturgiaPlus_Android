package org.deiverbum.app.model;

public class CompletasDia {
    Salmodia salmodia;
    CompletasLectura completasLectura;
    String oracion;

    public Salmodia getSalmodia() {
        return this.salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }

    public CompletasLectura getCompletasLectura() {
        return this.completasLectura;
    }

    public void setCompletasLectura(CompletasLectura completasLectura) {
        this.completasLectura = completasLectura;
    }

    public String getOracion() {
        return this.oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

}
