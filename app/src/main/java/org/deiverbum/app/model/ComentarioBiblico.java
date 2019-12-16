package org.deiverbum.app.model;

public class ComentarioBiblico {
    private Obra obra;
    private String tema;
    private String refBiblia;
    private String refFuente;
    private String texto;
    private String padre;

    public ComentarioBiblico() {
    }

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
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
