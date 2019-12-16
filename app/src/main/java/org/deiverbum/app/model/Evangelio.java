package org.deiverbum.app.model;

import org.deiverbum.app.utils.Utils;

public class Evangelio {
    public String libro;
    public String ref;
    public String texto;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getEvangelioForRead() {
        return Utils.stripQuotation(texto);
    }


}