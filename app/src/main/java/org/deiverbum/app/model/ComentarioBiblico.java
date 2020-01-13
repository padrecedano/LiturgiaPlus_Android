package org.deiverbum.app.model;

public class ComentarioBiblico {
    private Obra obras;
    private String tema;
    private String refBiblia;
    private String refFuente;
    private String texto;
    private String padre;
    private String obra;
    private String cita;
    private String ref;

    public ComentarioBiblico() {
    }

    public String getCita() {
        return this.cita;
    }

    public void setCita(String cita) {
        this.cita = cita;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getObra() {
        return this.obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getTema() {
        return this.tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getRefBiblia() {
        return this.refBiblia;
    }

    public void setRefBiblia(String refBiblia) {
        this.refBiblia = refBiblia;
    }

    public String getRefFuente() {
        return this.refFuente;
    }

    public void setRefFuente(String refFuente) {
        this.refFuente = refFuente;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }


}
