package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Patristica {
    public String padre;
    public String obra;
    public String fuente;
    public String tema;
    public String texto;
    public String ref;
    public String responsorio;

    public Patristica() {
    }
    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTexto() {
        return texto;
    }

    public Spanned getTextoSpan() {
        Spanned str = Utils.fromHtml(Utils.getFormato(texto));
        return str;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getResponsorio() {
        return responsorio;
    }


    public Spanned getResponsorioSpan() {
        String r = "";
        if (responsorio != null && !responsorio.isEmpty() && !responsorio.equals("null")) {

            String[] arrPartes = responsorio.split("\\|");
            r = Utils.getResponsorio(arrPartes, 1);
        } else {
            r = responsorio;
        }
        return Utils.fromHtml(r);
    }

    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
    }

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("SEGUNDA LECTURA");
    }


    public String getResponsorioForReader() {
        String r = "";
        if (responsorio != null && !responsorio.isEmpty() && !responsorio.equals("null")) {

            String[] arrPartes = responsorio.split("\\|");
            r = Utils.getResponsorioForReader(arrPartes, 1);
        } else {
            r = responsorio;
        }
        return r;
    }


}
