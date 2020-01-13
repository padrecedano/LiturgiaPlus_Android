package org.deiverbum.app.model;

public class Laudes {
    private Himno himno;
    private Salmodia salmodia;
    private LecturaBreve lecturaBreve;
    private Benedictus benedictus;
    private Preces preces;
    private String oracion;

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

    public Benedictus getBenedictus() {
        return benedictus;
    }

    public void setBenedictus(Benedictus benedictus) {
        this.benedictus = benedictus;
    }

    public Preces getPreces() {
        return preces;
    }

    public void setPreces(Preces preces) {
        this.preces = preces;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

    public String getTituloHora() {
        return "LAUDES";
    }
}
