package org.deiverbum.app.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Biblica {
    private String libro;
    private String capitulo;
    private String vInicial;
    private String vFinal;
    private String tema;
    private String texto;
    private String ref;
    private String responsorio;

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(String capitulo) {
        this.capitulo = capitulo;
    }

    public String getInicial() {
        return vInicial;
    }

    public void setInicial(String vInicial) {
        this.vInicial = vInicial;
    }

    public String getFinal() {
        return vFinal;
    }

    public void setFinal(String vFinal) {
        this.vFinal = vFinal;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Spanned getTexto() {
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

    public Spanned getResponsorio() {
        String text = "";
        if (responsorio != null && !responsorio.isEmpty() && !responsorio.equals("null")) {

            String[] arrPartes = responsorio.split("\\|");
            text = Utils.getResponsorio(arrPartes, 1);
        } else {

            text = responsorio;
        }
        return Utils.fromHtml(text);
    }

    public void setResponsorio(String responsorio) {
        this.responsorio = responsorio;
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

    public SpannableStringBuilder getHeader() {

        return Utils.formatTitle("PRIMERA LECTURA");
    }


}
