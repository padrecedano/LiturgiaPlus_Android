package org.deiverbum.app.model;

public class Intermedia {
    private Himno himno;
    private Salmodia salmodia;
    private LecturaBreve lecturaBreve;
    private String oracion;
    private int hora;

    public Himno getHimno() {
        return himno;
    }

    public void setHimno(Himno himno) {
        this.himno = himno;
    }

    public Salmodia getSalmodia() {
        return salmodia;
    }

    public void setSalmodia(Salmodia salmodia) {
        this.salmodia = salmodia;
    }

    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
}